package com.example.duanthuctap.service.impl;

import com.example.duanthuctap.config.VnPayConfig;
import com.example.duanthuctap.dto.request.OrderPaymentRequest;
import com.example.duanthuctap.dto.request.TransactionRequest;
import com.example.duanthuctap.dto.response.*;
import com.example.duanthuctap.entity.*;
import com.example.duanthuctap.repository.*;
import com.example.duanthuctap.service.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private StatusOderDetailRepository statusOderDetailRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<OrderResponse> showAllOrder(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<OrderResponse> orderResponses = ordersRepository.showAllOrder(pageable);
        return orderResponses.getContent();
    }

    @Override
    public List<OrdersDetailResponse> fillOrderDetail(Integer id) {
        return orderDetailRepository.fillOrderDetail(id);
    }

    @Override
    public MessageResponse orderPayment(OrderPaymentRequest orderPaymentRequest) {
        User user = new User();
        user.setUserName(orderPaymentRequest.getFullName());
        user.setPhone(orderPaymentRequest.getPhoneNumber());
        user.setAddress(orderPaymentRequest.getAddress());
        userRepository.save(user);
        Orders orders = new Orders();
        Random random = new Random();
        int rand = random.nextInt(1000);
        String maHd = String.format("HD%03d", rand);
        orders.setCodeOrder(maHd);
        orders.setFullName(orderPaymentRequest.getFullName());
        orders.setPhone(orderPaymentRequest.getPhoneNumber());
        orders.setAddress(orderPaymentRequest.getAddress());
        orders.setEmail(orderPaymentRequest.getEmail());
        orders.setTotalPrice(orderPaymentRequest.getTotalPrice());
        orders.setCreatedAt(LocalDate.now());
        orders.setUser(user);
        orders.setOrderStatus(OrderStatus.builder().orderStatusId(1).build());
        ordersRepository.save(orders);

        for (int id :orderPaymentRequest.getCartList()) {
            Optional<CartItem> cartItem = cartItemRepository.findById(id);
            if (cartItem.isPresent()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrders(orders);
                orderDetail.setTotalPrice(orderPaymentRequest.getTotalPrice());
                orderDetail.setCreatedAt(LocalDate.now());
                orderDetail.setQuantity(cartItem.get().getQuantity());
                orderDetail.setProduct(cartItem.get().getProduct());
                orderDetail.setUnitPrice(cartItem.get().getProduct().getPrice());
                orderDetail.setStatusOderDetail(StatusOderDetail.builder().id(2).build());
                orderDetailRepository.save(orderDetail);
            }
        }
        return MessageResponse.builder().message("Đặt Hàng Thành Công").build();
    }

    @Override
    public MessageResponse updateStatusOrder(String status, Integer idOrder, Integer idOrderDetail, String name) {
        Optional<OrderStatus> orderStatus = orderStatusRepository.findByStatusName(status);
        if (orderStatus.isEmpty()) {
            return MessageResponse.builder().message("Status is null").build();
        }

        Optional<StatusOderDetail> statusOderDetail = statusOderDetailRepository.findByStatus(status);
        if (statusOderDetail.isEmpty()) {
            return MessageResponse.builder().message("Status is null").build();
        }

        Optional<Orders> orders = ordersRepository.findById(idOrder);
        if (orders.isEmpty()) {
            return MessageResponse.builder().message("Order is null").build();
        }

        Optional<Account> account = accountRepository.findByUserName(name);
        orders.get().setOrderStatus(orderStatus.get());
        orders.get().setStaff(account.get().getStaffList().get(0));
        ordersRepository.save(orders.get());
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(idOrderDetail);
        if (orders.isEmpty()) {
            return MessageResponse.builder().message("Order is null").build();
        }
        orderDetail.get().setStatusOderDetail(statusOderDetail.get());
        orderDetailRepository.save(orderDetail.get());
        return MessageResponse.builder().message("Update status order successfully").build();
    }

    @Override
    public PaymentResponse callPaymentApi(HttpServletRequest req, TransactionRequest transactionRequest) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";

        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnPayConfig.getIpAddress(req);
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(transactionRequest.getAmountParam() * 100) );
        vnp_Params.put("vnp_CurrCode", "VND");
        if ("NCB" != null && !"NCB".isEmpty()) {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thông tin đặt hàng");
        vnp_Params.put("vnp_OrderType", "Chuyển khoản");

        if ("vn" != null && !"vn".isEmpty()) {
            vnp_Params.put("vnp_Locale", "vn");
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        // Add Params of 2.0.1 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        // Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator iterator = fieldNames.iterator();
        while (iterator.hasNext()) {
            String fieldName = (String) iterator.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (iterator.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return PaymentResponse.builder().url("value" + VnPayConfig.vnp_PayUrl + "?" + queryUrl).build();
    }

    @Override
    public TransactionResponse payTransaction(TransactionRequest transactionRequest) {
        Payment payment = new Payment();
        payment.setCreatedAt(LocalDate.now());
        payment.setAmountParam(transactionRequest.getAmountParam());
        payment.setStatus(1);
        paymentRepository.save(payment);
        return TransactionResponse.builder().amountParam(transactionRequest.getAmountParam()).build();
    }

    @Override
    public Optional<Payment> findByPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

}

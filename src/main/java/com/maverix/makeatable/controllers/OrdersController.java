package com.maverix.makeatable.controllers;

import com.maverix.makeatable.dto.Orders.OrdersGetDto;
import com.maverix.makeatable.dto.Orders.OrdersPostDto;
import com.maverix.makeatable.dto.Orders.OrdersPutDto;
import com.maverix.makeatable.services.OrdersService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public ResponseEntity<Response<List<OrdersGetDto>>> getAllOrders() {
        List<OrdersGetDto> orders = ordersService.getAllOrders();
        Response<List<OrdersGetDto>> response = Response.<List<OrdersGetDto>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Orders retrieved successfully")
                .data(orders)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<OrdersGetDto>> getOrderById(@PathVariable Long id) {
        OrdersGetDto orders = ordersService.getOrderById(id);
        Response<OrdersGetDto> response = Response.<OrdersGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Order retrieved successfully")
                .data(orders)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<OrdersGetDto>> createOrder(@RequestBody OrdersPostDto ordersPostDto) {

        OrdersGetDto createdOrder = ordersService.createOrder(ordersPostDto);

        Response<OrdersGetDto> response = Response.<OrdersGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("Order created successfully")
                .data(createdOrder)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<OrdersGetDto>> updateOrder(@PathVariable Long id, @RequestBody OrdersPutDto ordersPutDto) {

        OrdersGetDto updatedOrder = ordersService.updateOrder(id, ordersPutDto);

        Response<OrdersGetDto> response = Response.<OrdersGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Order updated successfully")
                .data(updatedOrder)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrder(id);

        Response<Void> response = Response.<Void>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("Order deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}

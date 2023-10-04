package com.maverix.makeatable.controllers;

import com.maverix.makeatable.config.Security.JwtService;
import com.maverix.makeatable.dto.Orders.*;
import com.maverix.makeatable.exceptions.ResourceNotFoundException;
import com.maverix.makeatable.exceptions.UnauthorizedAccessException;
import com.maverix.makeatable.services.OrdersService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrdersController {

    private final OrdersService ordersService;
    private final JwtService jwtService;

    public OrdersController(OrdersService ordersService, JwtService jwtService) {
        this.ordersService = ordersService;
        this.jwtService = jwtService;
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("restaurant/{restaurantId}")
    public Response<List<OrderDetailsDTO>> getOrdersForRestaurant(@PathVariable Long restaurantId) {
        List<OrderDetailsDTO> orderDetailsDTOList = ordersService.getOrderDetailsByRestaurantId(restaurantId);
        return Response.<List<OrderDetailsDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Order details fetched successfully")
                .data(orderDetailsDTOList)
                .timeStamp(LocalDateTime.now())
                .build();
    }
    @PreAuthorize("hasAuthority('MANAGAER')")
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
    @PreAuthorize("hasAnyAuthority('MANAGER','CUSTOMER')")
    @GetMapping("/last/{userId}")
    public LastOrderDto getLastOrderForUser(@PathVariable Long userId) {
        return ordersService.getLastOrderForUser(userId);
    }
    @PreAuthorize("hasAuthority('MANAGER')")

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
    @PreAuthorize("hasAuthority('CUSTOMER')")
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
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<OrdersGetDto>> updateOrder(@PathVariable Long id,
                                                              @RequestBody OrdersPutDto ordersPutDto,
                                                              @RequestHeader("Authorization") String token) {

        Long userId = Long.valueOf(jwtService.extractId(token));
        if (userId != null && !ordersService.isOrderBelongsToUser(id, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Response.<OrdersGetDto>builder()
                            .timeStamp(LocalDateTime.now())
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .status(HttpStatus.FORBIDDEN)
                            .message("Access denied: Order does not belong to the current user")
                            .data(null)
                            .build());
        }

        // Update the order
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


    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PutMapping("/{orderId}/reschedule")
    public ResponseEntity<Response<String>> rescheduleOrder(@PathVariable Long orderId,
                                                            @RequestBody OrderRescheduleDto rescheduleDto,
                                                            @RequestHeader("Authorization") String token) {

        try {
            // Extract user ID from the token
            Long userId = Long.valueOf(jwtService.extractId(token));

            // Check if the order belongs to the current user
            if (!ordersService.isOrderBelongsToUser(orderId, userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Response.<String>builder()
                                .timeStamp(LocalDateTime.now())
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .status(HttpStatus.FORBIDDEN)
                                .message("Access denied: Order does not belong to the current user")
                                .data(null)
                                .build());
            }
            boolean isRescheduled = ordersService.rescheduleOrder(orderId, rescheduleDto);

            String successMessage = isRescheduled ? "Order rescheduled successfully." : "No changes made to the order.";

            Response<String> response = Response.<String>builder()
                    .timeStamp(LocalDateTime.now())
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message(successMessage)
                    .build();

            return ResponseEntity.ok(response);
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Response.<String>builder()
                            .timeStamp(LocalDateTime.now())
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .status(HttpStatus.FORBIDDEN)
                            .message(e.getMessage())
                            .data(null)
                            .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.<String>builder()
                            .timeStamp(LocalDateTime.now())
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .status(HttpStatus.NOT_FOUND)
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
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

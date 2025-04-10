package com.finace.management.controller;

import com.finace.management.dto.request.AddItemReqDto;
import com.finace.management.dto.request.UpdateItemReqDto;
import com.finace.management.dto.response.ApiResponse;
import com.finace.management.dto.response.ItemResDto;
import com.finace.management.entity.AppUser;
import com.finace.management.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('STATUS_ACTIVE')")
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @Operation(summary = "Add new item")
    public ApiResponse<ItemResDto> addItem(@AuthenticationPrincipal AppUser currentUser,
                                           @RequestBody @Valid AddItemReqDto item) {
        return ApiResponse.<ItemResDto>builder()
                .result(itemService.addItem(currentUser, item))
                .message("Add new item successfully")
                .build();
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Update item")
    public ApiResponse<ItemResDto> updateItem(@AuthenticationPrincipal AppUser currentUser,
                                              @RequestBody @Valid UpdateItemReqDto item,
                                              @PathVariable Integer itemId) {
        return ApiResponse.<ItemResDto>builder()
                .result(itemService.updateItem(currentUser, item, itemId))
                .message("Update item successfully")
                .build();
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Delete item")
    public ApiResponse<ItemResDto> deleteItem(@PathVariable Integer itemId,
                                              @AuthenticationPrincipal AppUser currentUser) {
        return ApiResponse.<ItemResDto>builder()
                .result(itemService.deleteItem(currentUser, itemId))
                .message("Delete item successfully")
                .build();
    }
}

package com.criscode.order.utils;

import com.criscode.exceptionutils.NotFoundException;
import com.criscode.order.constants.ApplicationConstants;
import com.criscode.order.entity.StatusOrder;

public class HandleStatusOrder {
    public static StatusOrder handleStatus(String status) {
        switch (status) {
            case ApplicationConstants.NOT_PROCESSED:
                return StatusOrder.NOT_PROCESSED;
            case ApplicationConstants.PROCESSING:
                return StatusOrder.PROCESSING;
            case ApplicationConstants.SHIPPED:
                return StatusOrder.SHIPPED;
            case ApplicationConstants.DELIVERED:
                return StatusOrder.DELIVERED;
            case ApplicationConstants.CANCELLED:
                return StatusOrder.CANCELLED;
            default:
                throw new NotFoundException("Not find status order");
        }
    }
}

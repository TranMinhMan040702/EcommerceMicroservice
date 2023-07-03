package com.criscode.review.dto;

import com.criscode.clients.user.dto.UserReviewDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDto {

	private Integer id;
	
	private String content;

	private Integer rating;

	private UserReviewDto user;
	
	private Integer orderId;

	private Integer productId;
	
	private Boolean approved;

}

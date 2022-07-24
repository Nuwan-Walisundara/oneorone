package com.coffeeshop.service.model.custom;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
/**
 * unique order id	
 */
//@NotBlank(message = "[orderId] cannot be blank")	
private Long orderId;
@NotBlank(message = "[Status] cannot be blank")
private String status;
	
private String remarks;


}

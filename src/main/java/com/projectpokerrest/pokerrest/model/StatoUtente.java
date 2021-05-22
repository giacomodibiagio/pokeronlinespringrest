package com.projectpokerrest.pokerrest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public enum StatoUtente {

	CREATO, ATTIVO, DISABILITATO

}

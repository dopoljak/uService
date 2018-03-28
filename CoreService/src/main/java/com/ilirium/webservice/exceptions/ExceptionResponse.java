package com.ilirium.webservice.exceptions;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"correlation_id", "http_code", "http_message", "error_message", "error_extended_message"})
public class ExceptionResponse {

    public Integer http_code;
    public String http_message;
    public String error_message;
    public String error_extended_message;
    public String correlation_id;
}

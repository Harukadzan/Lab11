package com.example.labka11.exception;


//NotFoundException --  здесь мы создаем свою ошибку а RuntimeException это общая ошибка джавы
public class NotFoundException extends RuntimeException{
    public NotFoundException(String msg){
        super(msg);
    }
}

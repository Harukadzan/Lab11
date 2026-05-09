package com.example.labka11.exception;


//NotFoundException --  здесь мы создаем свою ошибку а RuntimeException это общая ошибка джавы
public class MaratovaAruzhanNotFoundException extends RuntimeException{
    public MaratovaAruzhanNotFoundException(String msg){
        super(msg);
    }
}

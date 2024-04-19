package com.ohgiraffers.springdatajpa.config;

import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 스프링 구성 클래스를 선언
public class BeanConfig {
    @Bean   // 메소드가 빈을 생성함을 의미
    public ModelMapper modelMapper() { // ModelMapper 빈을 생성하는 메소드를 정의
        /* setter 메소드 미사용 시 ModelMapper가
         * private 필드에 접근할 수 있도록 설정
         * setter 메소드를 사용하지 않을 경우 ModelMapper 가 private 필드에 접근할수 있도록 설정 */
        ModelMapper modelMapper = new ModelMapper(); // ModelMapper 객체를 생성
        modelMapper.getConfiguration() // ModelMapper의 설정을 가져온다
                .setFieldAccessLevel(   // 필드의 접근 수준을 설정
                        org.modelmapper.config.Configuration.AccessLevel.PRIVATE // private 필드에 접근할수 있또록 설정
                )
                .setFieldMatchingEnabled(true); //필드 매칭을 활성화
        return modelMapper; // 생성된 ModelMapper 객체를 반환
    }
}







package com.example.project.config;

import com.example.project.model.dto.response.PageToPaginationDTO;
import org.springframework.data.domain.Page;

public class ConvertPageToPaginationDTO {
    public static <T>PageToPaginationDTO<T> convertPageToPaginationDTO(Page<T> page)
    {
        PageToPaginationDTO<T> paginationDTO = new PageToPaginationDTO<>();
        paginationDTO.setPageSize(page.getSize());
        paginationDTO.setTotalPages(page.getTotalPages());
        paginationDTO.setCurrentPage(page.getNumber()+1);
        paginationDTO.setContent(page.getContent());
        paginationDTO.setTotalElement(page.getTotalElements());
        return paginationDTO;
    }
}

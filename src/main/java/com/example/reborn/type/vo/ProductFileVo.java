package com.example.reborn.type.vo;

import com.example.reborn.type.etc.CategoryName;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Getter
@Data
public class ProductFileVo {



        private Long price;
        private String title;
        private CategoryName categoryName;
        private List<MultipartFile> files;
        private Long receiptId;

}

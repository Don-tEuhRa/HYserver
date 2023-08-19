package com.example.reborn.type.dto;


import com.example.reborn.type.etc.PostCategory;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PostDto {
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9!?,.]*$")
    private String title;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9!?,.]*$")
    private String content;

    private boolean isSecret;
    @NotNull
    private PostCategory category;
    public  synchronized static boolean inputTest(PostDto dto) {



        if(dto.getTitle()==null)
            return false;
        if(dto.getContent()==null)
            return false;
        if(dto.category==null)
            return false;
        return true;
    }

}

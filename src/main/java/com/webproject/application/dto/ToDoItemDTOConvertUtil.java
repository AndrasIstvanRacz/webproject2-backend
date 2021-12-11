package com.webproject.application.dto;


import com.webproject.application.entity.ToDoItem;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ToDoItemDTOConvertUtil {

    public List<ToDoItemDTO> convertToDoItemListToToDoItemDTOList(List<ToDoItem> toDoItemList) {

        if(toDoItemList.isEmpty())
            return new ArrayList<>();

        List<ToDoItemDTO> toDoItemDTOList = new ArrayList<>();
        toDoItemList.forEach(toDoItem -> toDoItemDTOList.add(toDoItem2DTO(toDoItem)));
        return toDoItemDTOList;
    }

    public ToDoItem createEmptyToDoFromDTO(ToDoItemDTO toDoItemDTO){

        return ToDoItem.builder()
                .title(toDoItemDTO.getTitle())
                .build();
    }

    public ToDoItemDTO toDoItem2DTO(ToDoItem toDoItem){

        return ToDoItemDTO.builder()
                .id(toDoItem.getId())
                .title(toDoItem.getTitle()).build();
    }

 }

package com.ohgriaffers.pos.menu.model;

import com.ohgriaffers.pos.menu.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDAO {
    List<MenuDTO> selectAllMenu();

    MenuDTO selectMenu(MenuDTO menuDTO);


    void registMenu(MenuDTO newMenu);
}

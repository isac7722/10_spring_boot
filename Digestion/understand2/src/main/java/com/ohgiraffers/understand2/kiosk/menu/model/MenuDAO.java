package com.ohgiraffers.understand2.kiosk.menu.model;

import com.ohgiraffers.understand2.kiosk.menu.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDAO {
    List<MenuDTO> searchAllMenu();


    MenuDTO searchMenuByName(String name);

    int registMenu(MenuDTO newMenu);


    int deleteMenu(MenuDTO menuDTO);
}

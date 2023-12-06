package com.ohgriaffers.pos.menu.service;

import com.ohgriaffers.pos.menu.dto.MenuDTO;
import com.ohgriaffers.pos.menu.model.MenuDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MenuService {

    public MenuService(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    private MenuDAO menuDAO;

    public List<MenuDTO> selectAllMenu() {

        List<MenuDTO> menus = menuDAO.selectAllMenu();

        if (Objects.isNull(menus)){
            System.out.println("exception : menus가 없네요");
        }
        return menus;

    }

    public MenuDTO selectMenu(MenuDTO menuDTO) {

        MenuDTO menu = menuDAO.selectMenu(menuDTO);

        if(Objects.isNull(menu)){
            System.out.println("입력하신 메뉴가 없습니다.");
        }

        return menu;
    }

    public void registMenu(MenuDTO newMenu) {

        menuDAO.registMenu(newMenu);
    }
}

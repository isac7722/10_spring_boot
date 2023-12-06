package com.ohgiraffers.understand2.kiosk.menu.service;

import com.ohgiraffers.understand2.kiosk.menu.dto.MenuDTO;
import com.ohgiraffers.understand2.kiosk.menu.model.MenuDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class MenuService {

    public MenuService(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    private MenuDAO menuDAO;
    public List<MenuDTO> searchAllMenu() {

        List<MenuDTO> menuList = menuDAO.searchAllMenu();

        if (Objects.isNull(menuList)){
            System.out.println("존재하는 메뉴가 없습니다.");
        }
        return menuList;
    }

    public MenuDTO searchMenuByName(MenuDTO menuDTO) {

        return menuDAO.searchMenuByName(menuDTO.getName());

    }

    public int registMenu(MenuDTO newMenu) {
        System.out.println("test");
        int registResult = menuDAO.registMenu(newMenu);
        return registResult;
    }

    public int deleteMenu(MenuDTO menuDTO) {
        return menuDAO.deleteMenu(menuDTO);
    }
}

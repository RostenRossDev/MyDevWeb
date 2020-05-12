package com.rostenrossdev.rostenrossdev.util;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T>{
    private String url;
    private Page<T> page;

    private int totalPaginas;

    private int numElementoPorPagina;

    private int  paginaActual;

    private List<PageItem> paginas;
    public PageRender(String url,Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas=new ArrayList<>();
        this.numElementoPorPagina=page.getSize();
        this.totalPaginas=page.getTotalPages();
        this.paginaActual=page.getNumber() + 1;
        int desde, hasta;
        if (totalPaginas<= numElementoPorPagina) {
            desde=1;
            hasta=totalPaginas;
        } else {
            if (paginaActual <= numElementoPorPagina/2) {
                desde= 1;
                hasta=numElementoPorPagina;
            }else if(paginaActual>= totalPaginas-numElementoPorPagina){
                desde=totalPaginas-numElementoPorPagina+1;
                hasta=numElementoPorPagina;
            }else {
                desde=paginaActual - numElementoPorPagina/2;
                hasta=numElementoPorPagina;
            }
        }
        
        for (int i = 0; i < hasta; i++) {
            paginas.add(new PageItem(desde+i, paginaActual== desde+i));
        }
    }

    public String getUrl(){
        return this.url;
    }

    public int getTotalPaginas(){
        return this.totalPaginas;
    }
    
    public List<PageItem> getPaginas(){
        return this.paginas;
    }

    public int getPaginaActual(){
        return this.paginaActual;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }

    public boolean isHasPrevious(){
        return page.hasPrevious();
    }
}
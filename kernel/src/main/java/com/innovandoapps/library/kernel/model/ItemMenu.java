package com.innovandoapps.library.kernel.model;

public class ItemMenu {

    private final int imagen;
    private final String titulo;
    private final String subTititulo;

    private ItemMenu(ItemMenuBuilder builder) {
        this.imagen = builder.imagen;
        this.titulo = builder.titulo;
        this.subTititulo = builder.subTititulo;
    }

    public int getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubTititulo() {
        return subTititulo;
    }

    public static class ItemMenuBuilder{
        private int imagen;
        private String titulo;
        private String subTititulo;

        public ItemMenuBuilder() {}

        public ItemMenuBuilder imagen(int imagen){
            this.imagen = imagen;
            return this;
        }

        public ItemMenuBuilder titulo(String titulo){
            this.titulo = titulo;
            return this;
        }

        public ItemMenuBuilder subTititulo(String subTititulo){
            this.subTititulo = subTititulo;
            return this;
        }

        public ItemMenu build(){
            return new ItemMenu(this);
        }
    }
}

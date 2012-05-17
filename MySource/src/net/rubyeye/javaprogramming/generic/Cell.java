package net.rubyeye.javaprogramming.generic;

public class Cell<Z> {
    private Cell<Z> next;

    private Z element;

    public Cell(Z element) {
        this.element = element;
    }

    public Cell(Z element, Cell<Z> next) {
        this.next = next;
        this.element = element;
    }

    public Z getElement() {
        return element;
    }

    public void setElement(Z element) {
        this.element = element;
    }

    public Cell<Z> getNext() {
        return next;
    }

    public void setNext(Cell<Z> next) {
        this.next = next;
    }

}
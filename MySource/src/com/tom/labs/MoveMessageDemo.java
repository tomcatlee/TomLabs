package com.tom.labs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MoveMessageDemo extends JFrame
{
    
  public MoveMessageDemo()
  {
  MovableMessagePanel p = new MovableMessagePanel("welcome to java");
  p.setFont(new Font("Serif",Font.BOLD,24));
  setLayout(new BorderLayout());
  add(p);
  }
    
  public static void main(String[] args)
  {
  MoveMessageDemo frame = new MoveMessageDemo();
  frame.setLocationRelativeTo(null);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setTitle("MoveMessageDemo");
  frame.setSize(200, 100);
  frame.setVisible(true);
  }
    
  static class MovableMessagePanel extends JPanel
  {
  private String message = "Welcome to Java";
  private int x = 20;
  private int y = 20;
  public MovableMessagePanel(String s)
  {
  message = s;   
  addMouseMotionListener(new MouseMotionAdapter()
  {
  @Override
  public void mouseDragged(MouseEvent e)
  {
    
  x = e.getX();
  y = e.getY();   
  repaint();
  }
  });   
  }
    
  @Override
  protected void paintComponent(Graphics g)
  {
  super.paintComponent(g);   
  g.drawString(message, x, y);
  }
  }
}
package com.zjm.kindsmodels.composite;

import java.util.List;


public interface Ifile {
   public void display();
   public boolean add(Ifile file);
   public boolean delete(Ifile file);
   public List<Ifile> getChild();
   public List<Ifile> getChild2();
}

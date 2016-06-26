package test;

import org.junit.*;
import static org.junit.Assert.*;

import driver.JShell;
public class JShellTest {
  
  JShell mainShell;
  
  @Before
  public void setUp() {
    mainShell = new JShell();
  }

}

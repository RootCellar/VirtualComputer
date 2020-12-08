/*
 * RootCellar (github.com/rootcellar)
 * 12/07/2020
 * Creating virtual hardware in hopes of emulating a machine
 *
 *
*/

package VirtualComputer;

import VirtualComputer.Hardware.*;

public class VirtualComputer {


  public VirtualComputer() {

  }

  public static void main(String[] args) {

    Motherboard motherboard = new Motherboard(true);

    CPU processor = new CPU(motherboard);

  }

}

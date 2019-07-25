// <editor-fold defaultstate="expanded" desc="Project Notes">

/*
--------------------------------------------------------------------------------
  What the requirements specify
--------------------------------------------------------------------------------

Parts & Products
- inventory level cannot be lower than the minimum
- minimum level cannot be higher than the maximum
- maximum level cannot be lower than the minimum

Products
- must always have at least one associated part
- cannot be deleted if it has an associated part
    - the two above requirements are conflicting and if implemented as-is, it
      would be impossible to delete a product since modifying and deleting happen
      on two different screens
    - the FAQ states that a product may be deleted after confirmation even if
      parts are associated with it
- price cannot be less than the sum of its parts
- name must have a value
- price must have a value
- inventory must have a value
- inventory defaults to 0

Miscellaneous
- all delete and cancel actions require a confirmation dialog


--------------------------------------------------------------------------------
  What the requirements do not specify
--------------------------------------------------------------------------------

Parts & Products
- whether the min and max fields can be lower or higher than the current
  inventory level when being set
    - logically, it follows that the min cannot be set higher than the current
      inventory level and that the max cannot be set lower than the level
      since the inventory level must fall between min and max
    - however, that is not explicitely specified
    - Since this will be handled on a single screen effectively removing any
      chance that the min or max will be set independently, I'm going to ignore
      this and just be sure to set the inventory level after the min and max
      have been set
    - Ideally, this should be handled in the base classes, but for the purposes
      of this project, I'm going to ignore this ideal

Parts
- whether it can be deleted if it is associated with a product
- whether any fields are mandatory

Products
- whether min and max are mandatory
- whether a part can be associated with it more than once


--------------------------------------------------------------------------------
  Assumptions I've Made
--------------------------------------------------------------------------------

it's generally a bad idea to make assumptions, but for the sake of completeness,
I've done just that about missing specifications

Parts & Products
- assume that the min/max are optional
    - when they are not specified, no check will required when setting the
      inventory level

Parts
- assume that the requirements document intends for a part to also have
  mandatory name, price, and inventory fields, because:
    - logically a part should have a name if a product should have one
    - I suppose parts could be referenced (by humans) by id alone, but that 
      does not seem to be the intent since it is auto-generated
    - price will default to 0 automatically, so that particular requirement
      is met, regardless
    - since a part can also have specified min/max fields, it makes sense that
      the inventory should also be mandatory or should default to 0
    - inventory will default to 0, anyway
- assume that a part cannot be deleted if it is associated with a product
    - You'd end up with null references otherwise
    - Logically, you wouldn't want that to happen because it would invisibly
      modify a product's cost and the user wouldn't see those changes at the
      time of deletion or even which products are affected

Products
- assume that a part cannot be associated with it more than once
    - the word "association" is used instead of something like "component" and
      there is no way to specify a quantity for each associated part, 
      so it follows that a part can be associated only once


--------------------------------------------------------------------------------
  Liberties I've Taken
--------------------------------------------------------------------------------

- I made the following deviations from the UML diagram:
    - quite a few added properties and methods and whatnot
    - used ObservableLists instead of ArrayLists
    - since some of the functionality for both part and product are identical,
      I had them inherit from an Item base class to cut down on copy-paste code
      (hello DRY principle!)
        - I allowed this based on the following text in the instructions:
              "The UML Class Diagram may be altered so long as the aspects of the
              current UML diagram are intact"
        - the aspects remain intact in that the resulting classes still contain
          the correct fields
    - I have no idea what I'm supposed to do with the update methods that only
      have one int parameter, so I've implemented those methods, but left them
      blank
    - the inconsistent removeProduct and deletePart method names are really
      bothering me, but I've forced myself to leave them be
        - just putting that out there
        - seriously, fix this please
- I only implemented one screen for product editing and part editing
    - the add/edit screens are identical except for the title so why duplicate
      them? (DRY principle, again)
    - the title still shows "Update" when modifying an existing product/part
      and "Add" when adding a new product/part
- no JavaDoc comments
- no unit testing

*/

// </editor-fold>

package c482;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.*;
import javafx.collections.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class C482 extends Application {
    
    private List<Part> parts = Arrays.asList(
        new InhousePart   ( 1, "Oregano",            5.00,    5,    1,   10,   10),
        new OutsourcedPart( 2, "Garlic",             10.00,   10,   1,   20,   "The French Depot"),
        new InhousePart   ( 3, "Tomato",             10.50,   15,   1,   1000, 155),
        new InhousePart   ( 4, "Unlabeled Spice",    1.25,    3,    1,   1000, 10),
        new OutsourcedPart( 5, "Catnip",             200.00,  50,   1,   2000, "Catnip, Inc."),
        new InhousePart   ( 6, "Labeled Spice",      1.99,    100,  1,   1000, 10),
        new InhousePart   ( 7, "Dirty Spice",        1.20,    125,  1,   1000, 155),
        new InhousePart   ( 8, "Toad Parts",         100.27,  587,  100, 1000, 27),
        new OutsourcedPart( 9, "Baking Stuff",       502.99,  10,   0,   50,   "Baker's Market"),
        new OutsourcedPart(10, "Eu de Musk",         6.00,    50,   0,   50,   "Smells, Inc."),
        new OutsourcedPart(11, "Vampire Dust",       1.00,    50,   0,   50,   "The Undead are Okay, LLC."),
        new OutsourcedPart(12, "CEO's Tears",        9900.01, 1,    1,   5,    "CEO"),
        new OutsourcedPart(13, "Bucket of Stuff",    40.00,   196,  0,   5000, "Junkyard #8")
    );
    
    private List<Product> products = Arrays.asList(
        new Product(1, "Awesome Sauce",  2000, 25, 2, 1000, Arrays.asList(parts.get(5), parts.get(0), parts.get(1))),
        new Product(2, "Vague Sauce",    100,  50, 8, 1000, Arrays.asList(parts.get(3))),
        new Product(3, "Horrible Sauce", 1500, 75, 1, 1000, Arrays.asList(parts.get(0), parts.get(1), parts.get(2), parts.get(3))),
        new Product(4, "Spicy Ketchup",  500,  20, 4, 1000, Arrays.asList(parts.get(2), parts.get(4))),
        new Product(5, "Makeshift Cat",  9000, 90, 0, 1000, Arrays.asList(parts.get(4), parts.get(5)))
    );
    
    private Inventory inventory = new Inventory(parts, products);
    
    @Override
    public void start(Stage stage) throws Exception {
        WindowController controller = new WindowController(stage, inventory);
        stage.setHeight(500);
        stage.setWidth(800);
        controller.loadMain();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

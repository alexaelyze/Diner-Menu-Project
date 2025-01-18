/*
Villar, Alexandra Elyze
FA2.2 LE#4 - CSS124L
FOPI01
*/

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int CHICKEN_SALAD_PRICE = 100;
    private static final int TUNA_SALAD_PRICE = 150;
    private static final int VEGETABLE_SALAD_PRICE = 200;
    private static final int SODA_PRICE = 50;
    private static final int JUICE_PRICE = 150;
    private static final int MILK_TEA_PRICE = 100;

    private String saladChoice;
    private String drinkChoice;
    private String specialRequest;
    private boolean waterSelected = false;

    // Declare specialRequestField as an instance variable
    private TextField specialRequestField;
    private ComboBox<String> menuSelectionComboBox;
    private ToggleGroup drinkGroup;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem placeOrderItem = new MenuItem("Place the Order");
        MenuItem clearOrderItem = new MenuItem("Clear the Order");
        MenuItem displayOrderItem = new MenuItem("Display the Order");
        MenuItem exitItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(placeOrderItem, clearOrderItem, displayOrderItem, new SeparatorMenuItem(), exitItem);

        Menu editMenu = new Menu("Edit");
        MenuItem menuSelectionItem = new MenuItem("Menu Selection");
        MenuItem drinkSelectionItem = new MenuItem("Drink Selection");
        MenuItem specialRequestItem = new MenuItem("Special Request");
        editMenu.getItems().addAll(menuSelectionItem, drinkSelectionItem, specialRequestItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().addAll(aboutItem);

        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        root.setTop(menuBar);

        // Main Content
        VBox mainContent = new VBox(10);
        mainContent.setPadding(new Insets(10));

        // Salad Selection (Menu Selection)
        VBox saladSelection = new VBox(10);
        Label saladTitle = new Label("Menu Selection");
        saladSelection.getChildren().add(saladTitle);
        menuSelectionComboBox = new ComboBox<>();
        menuSelectionComboBox.getItems().addAll("Chicken Salad", "Tuna Salad", "Vegetable Salad");
        saladSelection.getChildren().add(menuSelectionComboBox);
        mainContent.getChildren().add(saladSelection);

        // Drink Selection
        VBox drinkSelection = new VBox(10);
        Label drinkLabel = new Label("Drink Selection:");
        drinkGroup = new ToggleGroup();
        RadioButton sodaRadio = new RadioButton("Soda");
        sodaRadio.setToggleGroup(drinkGroup);
        RadioButton juiceRadio = new RadioButton("Juice");
        juiceRadio.setToggleGroup(drinkGroup);
        RadioButton milkTeaRadio = new RadioButton("Milk Tea");
        milkTeaRadio.setToggleGroup(drinkGroup);
        drinkSelection.getChildren().addAll(drinkLabel, sodaRadio, juiceRadio, milkTeaRadio);

        // Place Salad Selection and Drink Selection side by side
        HBox selectionBox = new HBox(10);
        selectionBox.getChildren().addAll(saladSelection, drinkSelection);
        mainContent.getChildren().add(selectionBox);

        // Special Request
        VBox specialRequestBox = new VBox(10);
        Label specialRequestTitle = new Label("Special Request");
        specialRequestField = new TextField();
        specialRequestField.setPromptText("Special Request");
        specialRequestBox.getChildren().addAll(specialRequestTitle, specialRequestField);
        mainContent.getChildren().add(specialRequestBox);

        // Water Selection
        CheckBox waterCheckBox = new CheckBox("Water");
        waterCheckBox.setOnAction(event -> waterSelected = waterCheckBox.isSelected());
        mainContent.getChildren().add(waterCheckBox);

        root.setCenter(mainContent);

        // Handlers
        placeOrderItem.setOnAction(event -> {
            placeOrder();
        });

        clearOrderItem.setOnAction(event -> {
            clearOrder();
        });

        displayOrderItem.setOnAction(event -> {
            displayOrder();
        });

        exitItem.setOnAction(event -> {
            primaryStage.close();
        });

        menuSelectionItem.setOnAction(event -> {
            editMenuSelection();
        });

        drinkSelectionItem.setOnAction(event -> {
            editDrinkSelection();
        });

        specialRequestItem.setOnAction(event -> {
            editSpecialRequest();
        });

        aboutItem.setOnAction(event -> {
            about();
        });

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Diner");
        primaryStage.show();
    }

    private void placeOrder() {
    String selectedSalad = menuSelectionComboBox.getValue();
    RadioButton selectedDrink = (RadioButton) drinkGroup.getSelectedToggle();
    if (selectedSalad == null || selectedDrink == null) {
        showErrorAlert("Please select a salad and a drink.");
        return;
    }

    saladChoice = selectedSalad;
    drinkChoice = selectedDrink.getText();
    specialRequest = specialRequestField.getText();

    // Calculate total cost based on the selected salad and drink
    int saladPrice = 0;
    int drinkPrice = 0;

    if (saladChoice.equals("Chicken Salad")) {
        saladPrice = CHICKEN_SALAD_PRICE;
    } else if (saladChoice.equals("Tuna Salad")) {
        saladPrice = TUNA_SALAD_PRICE;
    } else if (saladChoice.equals("Vegetable Salad")) {
        saladPrice = VEGETABLE_SALAD_PRICE;
    }

    if (drinkChoice.equals("Soda")) {
        drinkPrice = SODA_PRICE;
    } else if (drinkChoice.equals("Juice")) {
        drinkPrice = JUICE_PRICE;
    } else if (drinkChoice.equals("Milk Tea")) {
        drinkPrice = MILK_TEA_PRICE;
    }

    int totalCost = saladPrice + drinkPrice;

    // Display order details in console
    if (waterSelected)
        System.out.println("Water: Yes (No Charge)");
    else
        System.out.println("Water: No");

    System.out.println("Salad: " + saladChoice);
    System.out.println("Drink: " + drinkChoice);
    System.out.println("Special Request: " + specialRequest);
    System.out.println("Total Cost: $" + totalCost);
    System.out.println(); // Add a newline after each order
}

    private void clearOrder() {
        menuSelectionComboBox.getSelectionModel().clearSelection();
        drinkGroup.selectToggle(null);
        specialRequestField.clear();
        waterSelected = false;
    }

    private void displayOrder() {
        if (saladChoice == null || drinkChoice == null) {
            showErrorAlert("No order placed yet.");
        } else {
            if (waterSelected)
                System.out.println("Water: Yes (No Charge)");
            else
                System.out.println("Water: No");

            System.out.println("Salad: " + saladChoice);
            System.out.println("Drink: " + drinkChoice);
            System.out.println("Special Request: " + (specialRequest.isEmpty() ? "None" : specialRequest));
        }
    }

    private void editMenuSelection() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Edit");
    alert.setHeaderText(null);
    alert.setContentText("Salad menu selection cleared.");
    alert.showAndWait();
    menuSelectionComboBox.getSelectionModel().clearSelection();
}

private void editDrinkSelection() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Edit");
    alert.setHeaderText(null);
    alert.setContentText("Drink selection cleared.");
    alert.showAndWait();
    drinkGroup.selectToggle(null);
}

private void editSpecialRequest() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Edit");
    alert.setHeaderText(null);
    alert.setContentText("Special request cleared.");
    alert.showAndWait();
    specialRequestField.clear();
}

    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Diner GUI\nVersion 1.0");
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

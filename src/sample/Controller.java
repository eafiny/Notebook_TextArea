package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {
    @FXML
    public VBox vbox;
    @FXML
    public TextField deleteTextField;
    @FXML
    public Button btnSave;

    private String currentTopic;
    public List<String> topic1Tasks;
    public List<String> topic2Tasks;

    @FXML
    public Button btnAdd;
    @FXML
    public Button btnDel;
    @FXML
    public Button btnTopic1;
    @FXML
    public Button btnTopic2;
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField1;


    @FXML
    public void initialize(){     //метод срабатывает при запуске приложения
        topic1Tasks = readTasks("Learn");
        topic2Tasks = readTasks("Work");
    }

    private List<String> readTasks(String currentTopic) {   //считывание заданий из файлов
        List<String> lines  = new ArrayList<>();
        try {
            File file = new File("" + currentTopic + ".txt");
            if (!file.exists()) {
                System.err.printf("File %s doesn't exist\n", file.getPath());
            } else {
                lines = Files.readAllLines(Paths.get(String.valueOf(file)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @FXML
    public void clickBtnAdd(ActionEvent actionEvent) {   //обработка нажатия кнопки "Добавить задачу"
        textField1.setVisible(true);
        textField1.requestFocus();
    }

    @FXML
    public void clickBtnDel(ActionEvent actionEvent) {  //обработка нажатия кнопки "Удалить"
        deleteTextField.setVisible(true);
        deleteTextField.requestFocus();
    }

    @FXML
    public void clickBtnTopic1(ActionEvent actionEvent) { //обработка нажатия кнопки "Учеба"
        currentTopic = "Learn";
        textArea.clear();
        for (String task:topic1Tasks) {
            textArea.appendText(task);
            textArea.appendText("\n\n");
        }
    }

    @FXML
    public void clickBtnTopic2(ActionEvent actionEvent) {  //обработка нажатия кнопки "Работа"
        currentTopic = "Work";
        textArea.clear();
        for (String task:topic2Tasks) {
            textArea.appendText(task);
            textArea.appendText("\n\n");
        }
    }

    @FXML
    public void textFieldClick(ActionEvent actionEvent) {  //обработка нажатия Enter на textField (id "textField") при добавлении новой задачи
        int index = 0;

        if (currentTopic.equals("Learn")) {
            index = Integer.parseInt(topic1Tasks.get(topic1Tasks.size() - 1).substring(0,1));
            System.out.println(index);
            topic1Tasks.add((index + 1) + ". " + textField1.getText());
            textArea.appendText((index + 1) + ". " + textField1.getText()+"\n\n");
        }else if(currentTopic.equals("Work")){
            index = Integer.parseInt(topic2Tasks.get(topic2Tasks.size() - 1).substring(0,1));
            System.out.println(index);
            topic2Tasks.add((index + 1) + ". " + textField1.getText());
            textArea.appendText((index + 1) + ". " + textField1.getText()+"\n\n");
        }

        textField1.clear();
        textField1.setVisible(false);
        btnSave.setStyle("-fx-background-color: #FA8072");  //изменение стиля компонента из кода, а не через файл .css
    }

    @FXML
    public void deleteTask(ActionEvent actionEvent) {       //обработка нажатия Enter на textField (id "deleteTextField") при удалении задачи из списка
        String number = deleteTextField.getText();
        List<String> tasks = null;
        if (currentTopic.equals("Learn")) {
            tasks = topic1Tasks;
        } else if (currentTopic.equals("Work")) {
            tasks = topic2Tasks;
        }
        for (String task:tasks) {
            if (task.substring(0,1).equals(number)) {
                tasks.remove(task);
            }
        }
        textArea.clear();
        for (String task:tasks) {
            textArea.appendText(task);
            textArea.appendText("\n\n");
        }
        deleteTextField.clear();
        deleteTextField.setVisible(false);
        btnSave.setStyle("-fx-background-color: #FA8072");  //изменение стиля компонента из кода, а не через файл .css
    }

    @FXML
    public void save(ActionEvent actionEvent) {             //сохранение списков задач в файлы
        File file1 = new File("Learn.txt");
        File file2 = new File("Work.txt");
        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(file1));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter(file2))){
            Iterator<String> itr = topic1Tasks.iterator();
            while (itr.hasNext()){
                writer1.write(itr.next());
                writer1.newLine();
            }
            itr = topic2Tasks.iterator();
            while (itr.hasNext()){
                writer2.write(itr.next());
                writer2.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnSave.setStyle("-fx-background-color: #3CB371");  //изменение стиля компонента из кода, а не через файл .css
    }
}

package guru.qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProductNanocadTest {

    @BeforeAll
    static void BeforeAll() {
        Configuration.browser = "FIREFOX";
        Configuration.browserSize = "1280x720";
    }

    @ValueSource(strings = {
            "nanoCAD BIM ВК",
            "nanoCAD BIM Вентиляция",
            "nanoCAD BIM Отопление",
            "nanoCAD BIM Электро",
            "nanoCAD BIM СКС",
            "nanoCAD BIM ОПС",
            "nanoCAD BIM Конструкции"})
    @ParameterizedTest(name = "Тестирование перехода на страницу продукта: {0}")
    void linkProductTest(String testData) {
        open("https://nanocad.ru");
        $("#navbarDropdown").click();
        $(byText(testData)).click();
        $("h1").shouldBe(text(testData));
    }

    @CsvSource(value = {
            "nanoCAD Металлоконструкции, Дистрибутив nanoCAD Металлоконструкции 22",
            "nanoCAD Стройплощадка, Дистрибутив nanoCAD Стройплощадка 22",
            "nanoCAD GeoniCS, Дистрибутив nanoCAD GeoniCS"
    })
    @ParameterizedTest(name = "Тестирование перехода на страницу скачивания продукта: {0}")
    void searchProductTest(String testData, String expectedResult) {
        open("https://nanocad.ru");
        $("#navbarDropdown").click();
        $(byText(testData)).click();
        $(".main_section__buttons").$(byText("Скачать")).click();
        $("tbody").shouldBe(text(expectedResult));
    }

    static Stream<Arguments> searchNameProductTest() {
        return Stream.of(
                Arguments.of("nanoCAD Металлоконструкции", "Дистрибутив nanoCAD Металлоконструкции 22"),
                Arguments.of("nanoCAD Стройплощадка", "Дистрибутив nanoCAD Стройплощадка 22"),
                Arguments.of("nanoCAD GeoniCS", "Дистрибутив nanoCAD GeoniCS")
        );
    }

    @MethodSource("searchNameProductTest")
    @ParameterizedTest(name = "Тестирование перехода на страницу скачивания продукта: {0}")
    void searchNameProductTest(String testData, String expectedResult) {
        open("https://nanocad.ru");
        $("#navbarDropdown").click();
        $(byText(testData)).click();
        $(".main_section__buttons").$(byText("Скачать")).click();
        $("tbody").shouldBe(text(expectedResult));
    }
}

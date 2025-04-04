package org.defix.tests.services;

import org.defix.services.calculator.*;
import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.objects.DefaultMappedToken;
import org.defix.services.calculator.objects.RawToken;
import org.defix.services.calculator.abstractions.TokenType;
import org.defix.services.calculator.objects.MappedExpressionToken;
import org.defix.services.calculator.objects.MappedFunctionToken;
import org.defix.services.calculator.tokenStores.ProgrammerTokensStore;
import org.defix.services.calculator.tokenStores.SimpleTokensStore;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ServiceTests
public class CalculatorServiceTests {
    public record ExpressionTestObj(String expression, LinkedList<RawToken> tokens) {
    }

    public record FunctionTestObj(String function, LinkedList<LinkedList<RawToken>> tokenizedParams) {
    }

    public record MappedExpressionTestObj(String expression, LinkedList<MappedToken> tokens) {
    }

    public record ResultTestObj(String expression, double result) {
    }

    private final TokensStore simpleTokensStore = new SimpleTokensStore();
    private final TokensStore programmerTokensStore = new ProgrammerTokensStore();

    @ParameterizedTest
    @MethodSource("provideExpressionObjs")
    public void expressionTokenizerTest(ExpressionTestObj testObj) {
        List<RawToken> tokens = new ExpressionTokenizer(simpleTokensStore).tokenizeExpression(testObj.expression);

        assertFalse(tokens.isEmpty());
        assertEquals(testObj.tokens.size(), tokens.size());

        for (int i = 0; i < tokens.size(); i++) {
            RawToken expectedToken = testObj.tokens.get(i);
            RawToken actualToken = tokens.get(i);
            assertEquals(expectedToken.getValue(), actualToken.getValue());
            assertEquals(expectedToken.getType(), actualToken.getType());
        }
    }

    @ParameterizedTest
    @MethodSource("provideTokenizedParamObjs")
    public void functionTokenizerTest(FunctionTestObj testObj) {
        List<List<RawToken>> tokenizedParams = new FunctionTokenizer(simpleTokensStore).tokenizeFunction(testObj.function);

        assertFalse(tokenizedParams.isEmpty());
        assertEquals(testObj.tokenizedParams.size(), tokenizedParams.size());

        for (int i = 0; i < tokenizedParams.size(); i++) {
            for (int j = 0; j < tokenizedParams.get(i).size(); j++) {
                RawToken expectedToken = testObj.tokenizedParams.get(i).get(j);
                RawToken actualToken = tokenizedParams.get(i).get(j);
                assertEquals(expectedToken.getType(), actualToken.getType());
                assertEquals(expectedToken.getValue(), actualToken.getValue());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideMappedExpressionObjs")
    public void tokenTreeBuilderTest(MappedExpressionTestObj testObj) {
        List<MappedToken> mappedTokens = new TokensTreeBuilder(simpleTokensStore)
                .build(new ExpressionTokenizer(simpleTokensStore).tokenizeExpression(
                testObj.expression
        ));

        assertFalse(mappedTokens.isEmpty());
        assertEquals(testObj.tokens.size(), mappedTokens.size());

        for (int i = 0; i < mappedTokens.size(); i++) {
            MappedToken expectedToken = testObj.tokens.get(i);
            MappedToken actualToken = mappedTokens.get(i);
            assertEquals(expectedToken.identify(), actualToken.identify());
        }
    }

    @ParameterizedTest
    @MethodSource("provideSimpleObjs")
    public void calculatorSimpleTest(ResultTestObj testObj) {
        assertEquals(testObj.result, Calculator.calculate(testObj.expression, new SimpleTokensStore()));
    }

    @ParameterizedTest
    @MethodSource("provideProgrammerObjs")
    public void calculatorProgrammerTest(ResultTestObj testObj) {
        assertEquals(testObj.result, Calculator.calculate(testObj.expression, new ProgrammerTokensStore()));
    }

    private static Stream<ResultTestObj> provideProgrammerObjs() {
        return Stream.of(
                new ResultTestObj("5 & 3 | 2 ^ 1 << 1", 1)
        );
    }

    private static Stream<ResultTestObj> provideSimpleObjs() {
        return Stream.of(
                new ResultTestObj("1+1-4", -2),
                new ResultTestObj("1+1-44.5", -42.5),
                new ResultTestObj("1+sqrt(2+2)-44.5", -41.5),
                new ResultTestObj("1+sqrt(2+2)-44.5*(3+3*3)", -531),
                new ResultTestObj("1+sqrt(2+2)-44.5*(3+pow(2,sqrt(4))*3)", -664.5),
                new ResultTestObj("-1", -1),
                new ResultTestObj("1", 1),
                new ResultTestObj("sqrt(pow(sqrt(pow(sqrt(pow(sqrt(256), 4)), 3)), 2))", 4096),
                new ResultTestObj("sin(sqrt(0))", 0),
                new ResultTestObj("cos(sqrt(0))", 1),
                new ResultTestObj("2^4", 16),
                new ResultTestObj("2^(1-1)", 1),
                new ResultTestObj("44,5", 44.5)
        );
    }

    private static Stream<FunctionTestObj> provideTokenizedParamObjs() {
        return Stream.of(
                new FunctionTestObj("sqrt(1+1)", new LinkedList<>(List.of(
                        new LinkedList<>(List.of(
                                new RawToken(TokenType.OPERAND, "1"),
                                new RawToken(TokenType.OPERATOR, "+"),
                                new RawToken(TokenType.OPERAND, "1")
                        ))
                )))
        );
    }

    private static Stream<MappedExpressionTestObj> provideMappedExpressionObjs() {
        return Stream.of(
                new MappedExpressionTestObj("1+1-4", new LinkedList<>(List.of(
                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                        new DefaultMappedToken(TokenType.OPERATOR, "+"),
                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                        new DefaultMappedToken(TokenType.OPERATOR, "-"),
                        new DefaultMappedToken(TokenType.OPERAND, "4")
                ))),
                new MappedExpressionTestObj("1+1-44,5", new LinkedList<>(List.of(
                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                        new DefaultMappedToken(TokenType.OPERATOR, "+"),
                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                        new DefaultMappedToken(TokenType.OPERATOR, "-"),
                        new DefaultMappedToken(TokenType.OPERAND, "44,5")
                ))),
                new MappedExpressionTestObj("1+sqrt(1+1)-44,5", new LinkedList<>(List.of(
                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                        new DefaultMappedToken(TokenType.OPERATOR, "+"),
                        new MappedFunctionToken("sqrt", new LinkedList<>(List.of(
                                new LinkedList<>(List.of(
                                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                                        new DefaultMappedToken(TokenType.OPERATOR, "+"),
                                        new DefaultMappedToken(TokenType.OPERAND, "1"))
                                )
                        ))),
                        new DefaultMappedToken(TokenType.OPERATOR, "-"),
                        new DefaultMappedToken(TokenType.OPERAND, "44,5")
                ))),
                new MappedExpressionTestObj("1+sqrt(1+1)-44,5*(3+3*3)", new LinkedList<>(List.of(
                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                        new DefaultMappedToken(TokenType.OPERATOR, "+"),
                        new MappedFunctionToken("sqrt", new LinkedList<>(List.of(
                                new LinkedList<>(List.of(
                                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                                        new DefaultMappedToken(TokenType.OPERATOR, "+"),
                                        new DefaultMappedToken(TokenType.OPERAND, "1"))
                                )
                        ))),
                        new DefaultMappedToken(TokenType.OPERATOR, "-"),
                        new DefaultMappedToken(TokenType.OPERAND, "44,5"),
                        new DefaultMappedToken(TokenType.OPERATOR, "*"),
                        new MappedExpressionToken(new LinkedList<>(List.of(
                                new DefaultMappedToken(TokenType.OPERAND, "3"),
                                new DefaultMappedToken(TokenType.OPERATOR, "+"),
                                new DefaultMappedToken(TokenType.OPERAND, "3"),
                                new DefaultMappedToken(TokenType.OPERATOR, "*"),
                                new DefaultMappedToken(TokenType.OPERAND, "3")
                        )))
                ))),
                new MappedExpressionTestObj("1+sqrt(1, 2)-(3*3-3)", new LinkedList<>(List.of(
                        new DefaultMappedToken(TokenType.OPERAND, "1"),
                        new DefaultMappedToken(TokenType.OPERATOR, "+"),
                        new MappedFunctionToken("sqrt", new LinkedList<>(List.of(
                                new LinkedList<>(List.of(
                                        new DefaultMappedToken(TokenType.OPERAND, "1")
                                )),
                                new LinkedList<>(List.of(
                                        new DefaultMappedToken(TokenType.OPERAND, "2")
                                ))
                        ))),
                        new DefaultMappedToken(TokenType.OPERATOR, "-"),
                        new MappedExpressionToken(new LinkedList<>(List.of(
                                new DefaultMappedToken(TokenType.OPERAND, "3"),
                                new DefaultMappedToken(TokenType.OPERATOR, "*"),
                                new DefaultMappedToken(TokenType.OPERAND, "3"),
                                new DefaultMappedToken(TokenType.OPERATOR, "-"),
                                new DefaultMappedToken(TokenType.OPERAND, "3")
                        )))
                )))
        );
    }

    private static Stream<ExpressionTestObj> provideExpressionObjs() {
        return Stream.of(
                new ExpressionTestObj("1+1-4", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "4")
                ))),
                new ExpressionTestObj("1+1-44,5", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44,5")
                ))),
                new ExpressionTestObj("1+1-44.5", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44.5")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.FUNCTION, "sqrt(1)"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44,5")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5*(3+3*3)", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.FUNCTION, "sqrt(1)"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44,5"),
                        new RawToken(TokenType.OPERATOR, "*"),
                        new RawToken(TokenType.EXPRESSION, "3+3*3")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5*(3+3*3-(2*2+(1+1)))-pow((1+1), 2)", new LinkedList<>(List.of(
                        new RawToken(TokenType.OPERAND, "1"),
                        new RawToken(TokenType.OPERATOR, "+"),
                        new RawToken(TokenType.FUNCTION, "sqrt(1)"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.OPERAND, "44,5"),
                        new RawToken(TokenType.OPERATOR, "*"),
                        new RawToken(TokenType.EXPRESSION, "3+3*3-(2*2+(1+1))"),
                        new RawToken(TokenType.OPERATOR, "-"),
                        new RawToken(TokenType.FUNCTION, "pow((1+1),2)")
                )))
        );
    }
}

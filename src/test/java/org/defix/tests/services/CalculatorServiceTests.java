package org.defix.tests.services;

import lombok.AllArgsConstructor;
import org.defix.services.calculator.CalculatorTokenizer;
import org.defix.services.calculator.TokenTreeBuilder;
import org.defix.services.calculator.abstractions.Function;
import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.objects.DefaultMappedToken;
import org.defix.services.calculator.objects.ExpressionToken;
import org.defix.services.calculator.abstractions.TokenType;
import org.defix.services.calculator.objects.MappedExpressionToken;
import org.defix.services.calculator.objects.MappedFunctionToken;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ServiceTests
public class CalculatorServiceTests {
    public record ExpressionTestObj(String expression, LinkedList<ExpressionToken> tokens) {
    }

    public record FunctionTestObj(String function, LinkedList<LinkedList<ExpressionToken>> tokenizedParams) {
    }

    public record MappedExpressionTestObj(String expression, LinkedList<MappedToken> tokens) {
    }

    @ParameterizedTest
    @MethodSource("provideExpressionObjs")
    public void expressionTokenizerTest(ExpressionTestObj testObj) {
        LinkedList<ExpressionToken> tokens = CalculatorTokenizer.tokenizeExpression(testObj.expression);

        assertFalse(tokens.isEmpty());
        assertEquals(testObj.tokens.size(), tokens.size());

        for (int i = 0; i < tokens.size(); i++) {
            ExpressionToken expectedToken = testObj.tokens.get(i);
            ExpressionToken actualToken = tokens.get(i);
            assertEquals(expectedToken.getValue(), actualToken.getValue());
            assertEquals(expectedToken.getType(), actualToken.getType());
        }
    }

    @ParameterizedTest
    @MethodSource("provideTokenizedParamObjs")
    public void functionTokenizerTest(FunctionTestObj testObj) {
        LinkedList<LinkedList<ExpressionToken>> tokenizedParams = CalculatorTokenizer.tokenizeFunctionParams(testObj.function);

        assertFalse(tokenizedParams.isEmpty());
        assertEquals(testObj.tokenizedParams.size(), tokenizedParams.size());

        for (int i = 0; i < tokenizedParams.size(); i++) {
            for(int j = 0; j < tokenizedParams.get(i).size(); j++) {
                ExpressionToken expectedToken = testObj.tokenizedParams.get(i).get(j);
                ExpressionToken actualToken = tokenizedParams.get(i).get(j);
                assertEquals(expectedToken.getType(), actualToken.getType());
                assertEquals(expectedToken.getValue(), actualToken.getValue());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideMappedExpressionObjs")
    public void tokenTreeBuilderTest(MappedExpressionTestObj testObj) {
        LinkedList<MappedToken> mappedTokens = TokenTreeBuilder.build(CalculatorTokenizer.tokenizeExpression(
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

    private static Stream<FunctionTestObj> provideTokenizedParamObjs() {
        return Stream.of(
                new FunctionTestObj("sqrt(1+1)", new LinkedList<>(List.of(
                        new LinkedList<>(List.of(
                                new ExpressionToken(TokenType.OPERAND, "1"),
                                new ExpressionToken(TokenType.OPERATOR, "+"),
                                new ExpressionToken(TokenType.OPERAND, "1")
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
                )))
        );
    }

    private static Stream<ExpressionTestObj> provideExpressionObjs() {
        return Stream.of(
                new ExpressionTestObj("1+1-4", new LinkedList<>(List.of(
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "+"),
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "-"),
                        new ExpressionToken(TokenType.OPERAND, "4")
                ))),
                new ExpressionTestObj("1+1-44,5", new LinkedList<>(List.of(
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "+"),
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "-"),
                        new ExpressionToken(TokenType.OPERAND, "44,5")
                ))),
                new ExpressionTestObj("1+1-44.5", new LinkedList<>(List.of(
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "+"),
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "-"),
                        new ExpressionToken(TokenType.OPERAND, "44.5")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5", new LinkedList<>(List.of(
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "+"),
                        new ExpressionToken(TokenType.FUNCTION, "sqrt(1)"),
                        new ExpressionToken(TokenType.OPERATOR, "-"),
                        new ExpressionToken(TokenType.OPERAND, "44,5")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5*(3+3*3)", new LinkedList<>(List.of(
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "+"),
                        new ExpressionToken(TokenType.FUNCTION, "sqrt(1)"),
                        new ExpressionToken(TokenType.OPERATOR, "-"),
                        new ExpressionToken(TokenType.OPERAND, "44,5"),
                        new ExpressionToken(TokenType.OPERATOR, "*"),
                        new ExpressionToken(TokenType.EXPRESSION, "3+3*3")
                ))),
                new ExpressionTestObj("1+sqrt(1)-44,5*(3+3*3-(2*2+(1+1)))-pow((1+1), 2)", new LinkedList<>(List.of(
                        new ExpressionToken(TokenType.OPERAND, "1"),
                        new ExpressionToken(TokenType.OPERATOR, "+"),
                        new ExpressionToken(TokenType.FUNCTION, "sqrt(1)"),
                        new ExpressionToken(TokenType.OPERATOR, "-"),
                        new ExpressionToken(TokenType.OPERAND, "44,5"),
                        new ExpressionToken(TokenType.OPERATOR, "*"),
                        new ExpressionToken(TokenType.EXPRESSION, "3+3*3-(2*2+(1+1))"),
                        new ExpressionToken(TokenType.OPERATOR, "-"),
                        new ExpressionToken(TokenType.FUNCTION, "pow((1+1),2)")
                )))
        );
    }
}

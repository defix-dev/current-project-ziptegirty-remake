package org.defix.services.calculator;

import lombok.AllArgsConstructor;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.objects.RawToken;

import java.util.LinkedList;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FunctionTokenizer {
    private final TokensStore store;

    public LinkedList<LinkedList<RawToken>> tokenizeFunction(String function) {
        int start = function.indexOf('(') + 1;
        int end = function.lastIndexOf(')');
        if (start <= 0 || end <= start) return new LinkedList<>();

        String content = function.substring(start, end);
        LinkedList<String> paramList = parseFunctionParams(content);

        return paramList.stream()
                .map(v -> new ExpressionTokenizer(store).tokenizeExpression(v))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static LinkedList<String> parseFunctionParams(String content) {
        LinkedList<String> paramList = new LinkedList<>();
        StringBuilder currentParam = new StringBuilder();

        int depth = 0;
        for (char c : content.toCharArray()) {
            if (c == '(') depth++;
            if (c == ')') depth--;

            if (c == ',' && depth == 0) {
                paramList.add(currentParam.toString().trim());
                currentParam.setLength(0);
            } else {
                currentParam.append(c);
            }
        }
        if (!currentParam.isEmpty()) paramList.add(currentParam.toString().trim());
        return paramList;
    }
}

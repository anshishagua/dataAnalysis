package com.anshishagua.utils;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.object.ParseResult;
import com.anshishagua.parser.grammar.ExpressionParser;
import com.anshishagua.parser.semantic.SemanticAnalyzer;
import com.anshishagua.service.SystemParameterService;
import com.anshishagua.service.TableService;

/**
 * User: lixiao
 * Date: 2018/5/24
 * Time: 上午10:37
 */

public class ParserTest {
    public static void main(String [] args) {
        String expression = "max(CASE WHEN 1 > 1 THEN 1.8888 ELSE '0' END) > '1'";

        expression = "current_date() > date_add(to_date('22222'), to_integer('111')";

        ExpressionParser parser = new ExpressionParser(expression);

        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace();

            return;
        }

        SemanticAnalyzer analyzer = new SemanticAnalyzer(parser.getAstTree(), ParseResult.ParseType.TAG_FILTER_CONDITION);
        analyzer.setTableService(new TableService());
        analyzer.setSystemParameterService(new SystemParameterService());

        try {
            analyzer.analyze();
        } catch (SemanticException ex) {
            ex.printStackTrace();

            return;
        }

        System.out.println(analyzer.getAstTree());
        System.out.println(analyzer.getAstTree().getResultType());
    }
}
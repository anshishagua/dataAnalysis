package com.anshishagua.parser.semantic;

import com.anshishagua.exceptions.SemanticException;
import com.anshishagua.parser.nodes.Node;
import com.anshishagua.parser.nodes.arithmetic.Divide;
import com.anshishagua.parser.nodes.arithmetic.Minus;
import com.anshishagua.parser.nodes.arithmetic.Mod;
import com.anshishagua.parser.nodes.arithmetic.Multiply;
import com.anshishagua.parser.nodes.arithmetic.Plus;
import com.anshishagua.parser.nodes.bool.And;
import com.anshishagua.parser.nodes.bool.Not;
import com.anshishagua.parser.nodes.bool.Or;
import com.anshishagua.parser.nodes.comparision.Equal;
import com.anshishagua.parser.nodes.comparision.GreaterThan;
import com.anshishagua.parser.nodes.comparision.GreaterThanOrEqual;
import com.anshishagua.parser.nodes.comparision.LessThan;
import com.anshishagua.parser.nodes.comparision.LessThanOrEqual;
import com.anshishagua.parser.nodes.comparision.NotEqual;
import com.anshishagua.parser.nodes.conditional.CaseWhen;
import com.anshishagua.parser.nodes.function.aggregation.Avg;
import com.anshishagua.parser.nodes.function.aggregation.Count;
import com.anshishagua.parser.nodes.function.aggregation.Sum;
import com.anshishagua.parser.nodes.priority.Parenthesis;
import com.anshishagua.parser.semantic.arithmetic.DivideResolver;
import com.anshishagua.parser.semantic.arithmetic.MinusResolver;
import com.anshishagua.parser.semantic.arithmetic.ModResolver;
import com.anshishagua.parser.semantic.arithmetic.MultiplyResolver;
import com.anshishagua.parser.semantic.arithmetic.PlusResolver;
import com.anshishagua.parser.semantic.bool.AndResolver;
import com.anshishagua.parser.semantic.bool.NotResolver;
import com.anshishagua.parser.semantic.bool.OrResolver;
import com.anshishagua.parser.semantic.comparison.EqualResolver;
import com.anshishagua.parser.semantic.comparison.GreaterThanOrEqualResolver;
import com.anshishagua.parser.semantic.comparison.GreaterThanResolver;
import com.anshishagua.parser.semantic.comparison.LessThanOrEqualResolver;
import com.anshishagua.parser.semantic.comparison.LessThanResolver;
import com.anshishagua.parser.semantic.comparison.NotEqualResolver;
import com.anshishagua.parser.semantic.function.aggregation.AvgResolver;
import com.anshishagua.parser.semantic.function.aggregation.CountResolver;
import com.anshishagua.parser.semantic.function.aggregation.SumResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/20
 * Time: 下午5:06
 */

public class TypeResolverRegistry {
    private static final Map<Class<? extends Node>, TypeResolver> resolverMap = new HashMap<>();

    private static void register(Class<? extends Node> clazz, TypeResolver resolver) {
        resolverMap.put(clazz, resolver);
    }

    static {
        register(Plus.class, new PlusResolver());
        register(Minus.class, new MinusResolver());
        register(Multiply.class, new MultiplyResolver());
        register(Divide.class, new DivideResolver());
        register(Mod.class, new ModResolver());

        register(Parenthesis.class, new ParenthesisResolver());

        register(CaseWhen.class, new CaseWhenResolver());

        register(Equal.class, new EqualResolver());
        register(NotEqual.class, new NotEqualResolver());
        register(GreaterThan.class, new GreaterThanResolver());
        register(GreaterThanOrEqual.class, new GreaterThanOrEqualResolver());
        register(LessThan.class, new LessThanResolver());
        register(LessThanOrEqual.class, new LessThanOrEqualResolver());

        register(And.class, new AndResolver());
        register(Or.class, new OrResolver());
        register(Not.class, new NotResolver());

        register(Sum.class, new SumResolver());
        register(Avg.class, new AvgResolver());
        register(Count.class, new CountResolver());
    }

    public static void resolve(Node node) throws SemanticException {
        Objects.requireNonNull(node);

        if (!resolverMap.containsKey(node.getClass())) {
            throw new SemanticException("No class " + node.getClass() + " registered");
        }

        resolverMap.get(node.getClass()).resolve(node);
    }
}
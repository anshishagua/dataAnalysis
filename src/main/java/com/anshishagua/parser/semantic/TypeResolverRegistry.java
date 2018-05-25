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
import com.anshishagua.parser.nodes.comparision.IsNotNull;
import com.anshishagua.parser.nodes.comparision.IsNull;
import com.anshishagua.parser.nodes.comparision.LessThan;
import com.anshishagua.parser.nodes.comparision.LessThanOrEqual;
import com.anshishagua.parser.nodes.comparision.Like;
import com.anshishagua.parser.nodes.comparision.NotEqual;
import com.anshishagua.parser.nodes.comparision.NotLike;
import com.anshishagua.parser.nodes.conditional.CaseWhen;
import com.anshishagua.parser.nodes.function.aggregation.Avg;
import com.anshishagua.parser.nodes.function.aggregation.Count;
import com.anshishagua.parser.nodes.function.aggregation.Max;
import com.anshishagua.parser.nodes.function.aggregation.Min;
import com.anshishagua.parser.nodes.function.aggregation.Sum;
import com.anshishagua.parser.nodes.function.condition.If;
import com.anshishagua.parser.nodes.function.conversion.ToBoolean;
import com.anshishagua.parser.nodes.function.conversion.ToDate;
import com.anshishagua.parser.nodes.function.conversion.ToDouble;
import com.anshishagua.parser.nodes.function.conversion.ToInteger;
import com.anshishagua.parser.nodes.function.conversion.ToLong;
import com.anshishagua.parser.nodes.function.conversion.ToString;
import com.anshishagua.parser.nodes.function.date.CurrentDate;
import com.anshishagua.parser.nodes.function.date.CurrentTimestamp;
import com.anshishagua.parser.nodes.function.date.DateAdd;
import com.anshishagua.parser.nodes.function.date.DateDiff;
import com.anshishagua.parser.nodes.function.date.Day;
import com.anshishagua.parser.nodes.function.date.Hour;
import com.anshishagua.parser.nodes.function.date.Minute;
import com.anshishagua.parser.nodes.function.date.Month;
import com.anshishagua.parser.nodes.function.date.Second;
import com.anshishagua.parser.nodes.function.date.Week;
import com.anshishagua.parser.nodes.function.date.Year;
import com.anshishagua.parser.nodes.function.numeric.Sin;
import com.anshishagua.parser.nodes.function.string.Concat;
import com.anshishagua.parser.nodes.function.string.Length;
import com.anshishagua.parser.nodes.function.string.LowerCase;
import com.anshishagua.parser.nodes.function.string.Replace;
import com.anshishagua.parser.nodes.function.string.Trim;
import com.anshishagua.parser.nodes.function.string.UpperCase;
import com.anshishagua.parser.nodes.priority.Parenthesis;
import com.anshishagua.parser.semantic.arithmetic.DivideResolver;
import com.anshishagua.parser.semantic.arithmetic.MinusResolver;
import com.anshishagua.parser.semantic.arithmetic.ModResolver;
import com.anshishagua.parser.semantic.arithmetic.MultiplyResolver;
import com.anshishagua.parser.semantic.arithmetic.PlusResolver;
import com.anshishagua.parser.semantic.function.math.SinResolver;
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
import com.anshishagua.parser.semantic.function.aggregation.MaxResolver;
import com.anshishagua.parser.semantic.function.aggregation.MinResolver;
import com.anshishagua.parser.semantic.function.aggregation.SumResolver;
import com.anshishagua.parser.semantic.function.condition.IfResolver;
import com.anshishagua.parser.semantic.function.condition.IsNotNullResolver;
import com.anshishagua.parser.semantic.function.condition.IsNullResolver;
import com.anshishagua.parser.semantic.function.condition.LikeResolver;
import com.anshishagua.parser.semantic.function.condition.NotLikeResolver;
import com.anshishagua.parser.semantic.function.conversion.ToBooleanResolver;
import com.anshishagua.parser.semantic.function.conversion.ToDateResolver;
import com.anshishagua.parser.semantic.function.conversion.ToDoubleResolver;
import com.anshishagua.parser.semantic.function.conversion.ToIntegerResolver;
import com.anshishagua.parser.semantic.function.conversion.ToLongResolver;
import com.anshishagua.parser.semantic.function.conversion.ToStringResolver;
import com.anshishagua.parser.semantic.function.date.CurrentDateResolver;
import com.anshishagua.parser.semantic.function.date.CurrentTimestampResolver;
import com.anshishagua.parser.semantic.function.date.DateAddResolver;
import com.anshishagua.parser.semantic.function.date.DateDiffResolver;
import com.anshishagua.parser.semantic.function.date.DayResolver;
import com.anshishagua.parser.semantic.function.date.HourResolver;
import com.anshishagua.parser.semantic.function.date.MinuteResolver;
import com.anshishagua.parser.semantic.function.date.MonthResolver;
import com.anshishagua.parser.semantic.function.date.SecondResolver;
import com.anshishagua.parser.semantic.function.date.WeekResolver;
import com.anshishagua.parser.semantic.function.date.YearResolver;
import com.anshishagua.parser.semantic.function.string.ConcatResolver;
import com.anshishagua.parser.semantic.function.string.LengthResolver;
import com.anshishagua.parser.semantic.function.string.LowerCaseResolver;
import com.anshishagua.parser.semantic.function.string.ReplaceResolver;
import com.anshishagua.parser.semantic.function.string.TrimResolver;
import com.anshishagua.parser.semantic.function.string.UpperCaseResolver;

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
        //Arithmetic
        register(Plus.class, new PlusResolver());
        register(Minus.class, new MinusResolver());
        register(Multiply.class, new MultiplyResolver());
        register(Divide.class, new DivideResolver());
        register(Mod.class, new ModResolver());

        //math
        register(Sin.class, new SinResolver());

        register(Parenthesis.class, new ParenthesisResolver());

        register(CaseWhen.class, new CaseWhenResolver());

        //Comparision
        register(Equal.class, new EqualResolver());
        register(NotEqual.class, new NotEqualResolver());
        register(GreaterThan.class, new GreaterThanResolver());
        register(GreaterThanOrEqual.class, new GreaterThanOrEqualResolver());
        register(LessThan.class, new LessThanResolver());
        register(LessThanOrEqual.class, new LessThanOrEqualResolver());

        //bool condition
        register(And.class, new AndResolver());
        register(Or.class, new OrResolver());
        register(Not.class, new NotResolver());

        //conditional
        register(If.class, new IfResolver());
        register(IsNull.class, new IsNullResolver());
        register(IsNotNull.class, new IsNotNullResolver());
        register(Like.class, new LikeResolver());
        register(NotLike.class, new NotLikeResolver());

        //string
        register(Concat.class, new ConcatResolver());
        register(Length.class, new LengthResolver());
        register(Trim.class, new TrimResolver());
        register(LowerCase.class, new LowerCaseResolver());
        register(UpperCase.class, new UpperCaseResolver());
        register(Replace.class, new ReplaceResolver());

        //aggregation
        register(Sum.class, new SumResolver());
        register(Avg.class, new AvgResolver());
        register(Count.class, new CountResolver());
        register(Max.class, new MaxResolver());
        register(Min.class, new MinResolver());

        //date
        register(CurrentTimestamp.class, new CurrentTimestampResolver());
        register(CurrentDate.class, new CurrentDateResolver());
        register(DateAdd.class, new DateAddResolver());
        register(DateDiff.class, new DateDiffResolver());
        register(Day.class, new DayResolver());
        register(Year.class, new YearResolver());
        register(Month.class, new MonthResolver());
        register(Week.class, new WeekResolver());
        register(Hour.class, new HourResolver());
        register(Minute.class, new MinuteResolver());
        register(Second.class, new SecondResolver());

        //conversion
        register(ToBoolean.class, new ToBooleanResolver());
        register(ToString.class, new ToStringResolver());
        register(ToDouble.class, new ToDoubleResolver());
        register(ToDate.class, new ToDateResolver());
        register(ToInteger.class, new ToIntegerResolver());
        register(ToLong.class, new ToLongResolver());
    }

    public static void resolve(Node node) throws SemanticException {
        Objects.requireNonNull(node);

        if (!resolverMap.containsKey(node.getClass())) {
            throw new SemanticException("No class " + node.getClass() + " registered");
        }

        resolverMap.get(node.getClass()).resolve(node);
    }
}
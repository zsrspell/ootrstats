package com.ootrstats.ootrstats.thymeleaf;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.Set;

public class MarkdownDialect extends AbstractDialect implements IExpressionObjectDialect {
    private final static String EXPRESSION_OBJECT_NAME = "markdown";

    public MarkdownDialect() {
        super(EXPRESSION_OBJECT_NAME);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {
            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton(EXPRESSION_OBJECT_NAME);
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return new MarkdownUtility();
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }
}

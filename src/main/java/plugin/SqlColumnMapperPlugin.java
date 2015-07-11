package plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SqlColumnMapperPlugin extends PluginAdapter {
    public SqlColumnMapperPlugin() {
        super();
    }
    
    public boolean validate(List<String> warnings) {
        return true;
    }
    
    @Override
    public boolean sqlMapBaseColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        String name = element.getName();
        if ("sql".equals(name)) {
            return sqlColumnMapperGenerated(element, introspectedTable);
        }
        return super.sqlMapBaseColumnListElementGenerated(element, introspectedTable);
    }
    
    /**
     * <pre>
     *  select m.user_id,m.user_name from user m
     *  ����
     *  m.user_id,m.user_name -> m.id,m.name
     * </pre>
     * 
     * @param element
     * @param introspectedTable
     * @return
     */
    private boolean sqlColumnMapperGenerated(XmlElement xmlElement, IntrospectedTable introspectedTable) {
        List<Element> elements = xmlElement.getElements();
        Element element = null;
        TextElement textElement = null;
        for (int i = 0; i < elements.size(); i++) {
            element = elements.get(i);
            if (element instanceof TextElement) {
                textElement = (TextElement)element;
                element = getElement(textElement, introspectedTable);
                elements.set(i, element);
            }
        }
        
        return true;
    }
    
    /**
     * ��ȡ��ѯ����
     * 
     * @param element
     * @param introspectedTable
     * @return
     */
    private Element getElement(TextElement element, IntrospectedTable introspectedTable) {
        if (isHandleContent(element)) {
            return getElementByHandleContent(element, introspectedTable);
        }
        
        return element;
    }
    
    /**
     * ��ȡ�����ѯ�ֶκ�Ĳ�ѯ����
     * 
     * @param element
     * @param introspectedTable
     * @return
     */
    private Element getElementByHandleContent(TextElement element, IntrospectedTable introspectedTable) {
        //m.user_id as m_user_id,m.user_name as m_user_name
        String content = element.getContent();
        
        //m.user_id as m_user_id
        String[] selectColumns = content.split(",");
        
        StringBuffer query = new StringBuffer();
        
        String[] columns = null;
        String aliasColumn = null, column = null, asField = null;
        for (String selectColumn : selectColumns) {
            if (query.length() > 0) {
                query.append(", ");
            }
            
            selectColumn = selectColumn.trim();
            if (selectColumn.length() <= 0) {
                continue;
            }
            
            //m.user_id
            aliasColumn = selectColumn.split("as")[0];
            query.append(aliasColumn);
            
            columns = aliasColumn.split("\\.");
            if (columns.length > 1) {
                column = columns[1].trim();
            } else {
                column = columns[0].trim();
            }
            
            asField = getAlias(column, introspectedTable);
            query.append(" as ").append(asField);
        }
        
        if (query.length() > 0) {
            TextElement textElement = new TextElement(query.toString());
            return textElement;
        }
        
        return element;
    }
    
    /**
     * �Ƿ����ѯ�ֶ�
     * 
     * @param element
     * @return
     */
    private boolean isHandleContent(TextElement element) {
        String content = element.getContent();
        if (content == null || content.trim().length() <= 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * ��ȡ����
     * 
     * @param column
     * @param introspectedTable
     * @return
     */
    private String getAlias(String column, IntrospectedTable introspectedTable) {
        String alias = getAliasByOriginal(column, introspectedTable);
        alias = getAliasByDelimited(column, alias, introspectedTable);
        return alias;
    }
    
    /**
     * ����ʵ������������������ȡ������/���ι��ı���
     * 
     * @param column
     * @param alias
     * @param introspectedTable
     * @return
     */
    private String getAliasByDelimited(String column, String alias, IntrospectedTable introspectedTable) {
        StringBuffer delimitedAlias = new StringBuffer(alias);
        
        IntrospectedColumn introspectedColumn = introspectedTable.getColumn(column);
        if (introspectedColumn != null && introspectedColumn.isColumnNameDelimited()) {
            delimitedAlias.insert(0, introspectedColumn.getContext().getBeginningDelimiter());
            delimitedAlias.append(introspectedColumn.getContext().getEndingDelimiter());
        }
        
        return delimitedAlias.toString();
    }
    
    /**
     * ����ʵ������������������ȡδ�������ι��ı���
     * 
     * @param column
     * @return
     */
    private String getAliasByOriginal(String column, IntrospectedTable introspectedTable) {
        String alias = getAliasFromPrimaryKeyColumns(column, introspectedTable);
        if (alias == null) {
            alias = getAliasFromBaseColumns(column, introspectedTable);
        }
        
        if (alias == null) {
            return column;
        }
        
        return alias;
    }
    
    /**
     * ����ʵ����������������������ȡ����
     * 
     * @param column
     * @param introspectedTable
     * @return
     */
    private String getAliasFromPrimaryKeyColumns(String column, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> baseColumns = introspectedTable.getPrimaryKeyColumns();
        String databaseColumnName = null, javaEntityFieldName = null;
        for (IntrospectedColumn baseColumn : baseColumns) {
            databaseColumnName = baseColumn.getActualColumnName();
            javaEntityFieldName = baseColumn.getJavaProperty();
            if (column.equals(databaseColumnName)) {
                return javaEntityFieldName;
            }
        }
        
        return null;
    }
    
    /**
     * ����ʵ�������ķ�������������������ȡ����
     * 
     * @param column
     * @param introspectedTable
     * @return
     */
    private String getAliasFromBaseColumns(String column, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
        String databaseColumnName = null, javaEntityFieldName = null;
        for (IntrospectedColumn baseColumn : baseColumns) {
            databaseColumnName = baseColumn.getActualColumnName();
            javaEntityFieldName = baseColumn.getJavaProperty();
            if (column.equals(databaseColumnName)) {
                return javaEntityFieldName;
            }
        }
        
        return null;
    }
}

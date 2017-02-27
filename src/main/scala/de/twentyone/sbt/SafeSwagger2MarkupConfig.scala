package de.twentyone.sbt

import java.util
import java.util.Comparator
import java.util.regex.Pattern

import com.google.common.collect.Ordering
import io.github.swagger2markup._
import io.github.swagger2markup.markup.builder.{LineSeparator, MarkupLanguage}
import io.github.swagger2markup.model.PathOperation
import io.swagger.models.HttpMethod
import io.swagger.models.parameters.Parameter

// Workaround for ugly classpath issue with commons-config lib
object SafeSwagger2MarkupConfig extends Swagger2MarkupConfig {
  val pathOpGetPath = new com.google.common.base.Function[PathOperation, String] {
    override def apply(f: PathOperation): String = f.getPath
  }
  val pathOpGetMethod = new com.google.common.base.Function[PathOperation, HttpMethod] {
    override def apply(f: PathOperation): HttpMethod = f.getMethod
  }
  val parameterGetName = new com.google.common.base.Function[Parameter, String] {
    override def apply(f: Parameter): String = f.getName
  }

  override def getMarkupLanguage: MarkupLanguage              = MarkupLanguage.MARKDOWN
  override def getSwaggerMarkupLanguage: MarkupLanguage       = MarkupLanguage.MARKDOWN
  override def isGeneratedExamplesEnabled: Boolean            = true
  override def isBasePathPrefixEnabled: Boolean               = false
  override def isSeparatedDefinitionsEnabled: Boolean         = false
  override def isSeparatedOperationsEnabled: Boolean          = false
  override def getPathsGroupedBy: GroupBy                     = GroupBy.AS_IS
  override def getOutputLanguage: Language                    = Language.EN
  override def isInlineSchemaEnabled: Boolean                 = true
  override def isInterDocumentCrossReferencesEnabled: Boolean = false
  override def isFlatBodyEnabled: Boolean                     = false
  override def isPathSecuritySectionEnabled: Boolean          = true
  override def getOverviewDocument: String                    = "overview"
  override def getPathsDocument: String                       = "paths"
  override def getDefinitionsDocument: String                 = "definitions"
  override def getSecurityDocument: String                    = "security"
  override def getSeparatedDefinitionsFolder: String          = "definitions"
  override def getSeparatedOperationsFolder: String           = "operations"
  override def getTagOrderBy: OrderBy                         = OrderBy.NATURAL
  override def getOperationOrderBy: OrderBy                   = OrderBy.NATURAL
  override def getParameterOrderBy: OrderBy                   = OrderBy.NATURAL
  override def getPropertyOrderBy: OrderBy                    = OrderBy.NATURAL
  override def getResponseOrderBy: OrderBy                    = OrderBy.NATURAL
  override def getDefinitionOrderBy: OrderBy                  = OrderBy.NATURAL
  override def getTagOrdering: Comparator[String]             = Ordering.natural()
  override def getOperationOrdering: Comparator[PathOperation] =
    Ordering
      .natural()
      .onResultOf(pathOpGetPath)
      .compound(
        Ordering
          .explicit[HttpMethod](HttpMethod.POST,
                                HttpMethod.GET,
                                HttpMethod.PUT,
                                HttpMethod.DELETE,
                                HttpMethod.PATCH,
                                HttpMethod.HEAD,
                                HttpMethod.OPTIONS)
          .onResultOf(pathOpGetMethod))
  override def getDefinitionOrdering: Comparator[String] = Ordering.natural()
  override def getPropertyOrdering: Comparator[String]   = Ordering.natural()
  override def getParameterOrdering: Comparator[Parameter] =
    Ordering.natural().onResultOf(parameterGetName)
  override def getResponseOrdering: Comparator[String] = Ordering.natural()

  override def getLineSeparator: LineSeparator = LineSeparator.UNIX
  override def isListDelimiterEnabled: Boolean = false
  override def getListDelimiter: Character     = ','

  override def getAnchorPrefix: String = null

  override def getExtensionsProperties: Swagger2MarkupProperties = null

  override def getHeaderPattern: Pattern = null

  override def getInterDocumentCrossReferencesPrefix: String = null

  override def getPageBreakLocations: util.List[PageBreakLocations] =
    new util.ArrayList[PageBreakLocations]()

}

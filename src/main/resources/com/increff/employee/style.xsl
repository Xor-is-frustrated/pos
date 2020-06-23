<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"    
        xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <!-- Attribute used for table border -->
    <xsl:attribute-set name="tableBorder">
          <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
    </xsl:attribute-set>
    <xsl:template match="Invoice">
        <fo:root>
              <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4"
                      page-height="29.7cm" page-width="21.0cm" margin="1cm">
                  <fo:region-body/>
                </fo:simple-page-master>
              </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
              <fo:flow flow-name="xsl-region-body">
                 <fo:block font-size="14pt" font-family="Helvetica"  space-after="5mm">
                      Order Receipt 
                      
                  </fo:block>
                  <fo:block font-size="10pt">
                      <fo:table table-layout="fixed" width="100%" border-collapse="separate">    
                        <fo:table-column column-width="4cm"/>
                        <fo:table-column column-width="4cm"/>
                        <fo:table-column column-width="4cm"/>
                            <fo:table-column column-width="4cm"/>
                            <fo:table-header>
                              <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                <fo:block font-weight="bold">Product</fo:block>
                              </fo:table-cell>
                            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                <fo:block font-weight="bold">Mrp</fo:block>
                            </fo:table-cell>
                            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                <fo:block font-weight="bold">Quantity</fo:block>
                            </fo:table-cell>
                            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                <fo:block font-weight="bold">Selling Price</fo:block>
                            </fo:table-cell>
                        </fo:table-header>
                        <fo:table-body>
                              <xsl:apply-templates select="items"/>
                        </fo:table-body>
                      </fo:table>
                  </fo:block>
                  <fo:block font-size="14pt" font-family="Helvetica"  space-after="5mm">
                      Total Cost
                      <xsl:value-of select="sellingprice"/>
                  </fo:block>
              </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="items">
    <fo:table-row>   
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="product"/>
        </fo:block>
      </fo:table-cell>
     
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="mrp"/>
        </fo:block>
      </fo:table-cell>   
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
      <fo:block>
          <xsl:value-of select="quantity"/>
      </fo:block>
      </fo:table-cell>
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
      <fo:block>
          <xsl:value-of select="sellingprice"/>
      </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>
</xsl:stylesheet>
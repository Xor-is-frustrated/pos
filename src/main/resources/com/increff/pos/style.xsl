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
              	<fo:block font-size="14pt" font-family="Helvetica"  space-after="1cm" text-align="center">
                      Increff PoS 
                      
                  </fo:block>
                 <fo:block font-size="8pt" font-family="Helvetica"  >  
                      Order Id:  
                      <xsl:value-of select="id"/>
                  </fo:block>
                  <fo:block font-size="8pt" font-family="Helvetica" space-after="1cm" >
                      Date:  
                      <xsl:value-of select="orderDate"/>
                  </fo:block>
                  <fo:block font-size="10pt">
                      <fo:table table-layout="fixed" width="100%" border-collapse="separate">    
                        <fo:table-column column-width="4.5cm" padding="4pt"  text-align="left"/>
                        <fo:table-column column-width="4.5cm" padding="4pt"  text-align="left"/>
                        <fo:table-column column-width="4.5cm" padding="4pt"   text-align="left"/>
                            <fo:table-column column-width="4.5cm" padding="4pt"  text-align="left"/>
                            <fo:table-header >
                              <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                                <fo:block font-weight="bold" padding="2pt" text-align="left"  margin-left="2pt" >Product</fo:block>
                              </fo:table-cell>
                            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                <fo:block font-weight="bold" padding="2pt" text-align="left"  margin-left="2pt" >Price</fo:block>
                            </fo:table-cell>
                            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                <fo:block font-weight="bold" padding="2pt" text-align="left"  margin-left="2pt">Quantity</fo:block>
                            </fo:table-cell>
                            <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                <fo:block font-weight="bold" padding="2pt" text-align="left" margin-left="2pt">Amount</fo:block>
                            </fo:table-cell>
                        </fo:table-header>
                        <fo:table-body font-size="8pt" margin-left="4pt">
                              <xsl:apply-templates select="items"/>
                        </fo:table-body>
                      </fo:table>
                  </fo:block>
                  <fo:block font-size="10pt" font-family="Helvetica"  space-before="1cm" space-after="5mm">
                      Total Amount: Rs  
                      <xsl:value-of select="sellingprice"/>
                  </fo:block>
              </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <xsl:template match="items">
    <fo:table-row>   
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block padding="2pt" text-align="left">
          <xsl:value-of select="product"/>
        </fo:block>
      </fo:table-cell>
     
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block padding="2pt" text-align="left">
          Rs <xsl:value-of select="sellingPrice"/>
        </fo:block>
      </fo:table-cell>   
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
      <fo:block padding="2pt" text-align="left"> 
          <xsl:value-of select="quantity"/>
      </fo:block>
      </fo:table-cell> 
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
      <fo:block padding="2pt" text-align="left">
          Rs <xsl:value-of select="itemTotalCost"/>
      </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>
</xsl:stylesheet>
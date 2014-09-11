 MERGE INTO PRODUCT p
 USING (select 1 from dual)
 ON ( P.PRODUCT_ID = 12101)
 WHEN MATCHED THEN
    UPDATE SET p.name = 'IPTVStandard',
               p.description = 'Free basic packet, include 8 channels',
               p.sales_period_start = '25/12/2010 00:00:00',
               p.sales_period_end = NULL,
               p.baseline_price = 0
WHEN NOT MATCHED THEN 
    INSERT (p.product_id,P.NAME ,P.DESCRIPTION,P.SALES_PERIOD_START,P.sales_period_end,P.BASELINE_PRICE)
    values(12101,'IPTVStandard','Free basic packet, include 8 channels','25/12/2010 00:00:00',NULL,0);

 MERGE INTO PRODUCT p
 USING (select 1 from dual)
 ON ( P.PRODUCT_ID = 12102)
 WHEN MATCHED THEN
    UPDATE SET p.name = 'IPTV11Plus',
               p.description = 'To basic 8 channels added 11 channels',
               p.sales_period_start = '14/03/2011 00:00:00',
               p.sales_period_end = NULL,
               p.baseline_price = 20
WHEN NOT MATCHED THEN 
    INSERT (p.product_id,P.NAME ,P.DESCRIPTION,P.SALES_PERIOD_START,P.sales_period_end,P.BASELINE_PRICE)
    values(12102,'IPTV11Plus','To basic 8 channels added 11 channels','14/03/2011 00:00:00',NULL,20);
      
MERGE INTO PRODUCT p
 USING (select 1 from dual)
 ON ( P.PRODUCT_ID = 12103)
 WHEN MATCHED THEN
    UPDATE SET  p.name = 'IPTVSport',
                p.description = '8 bacis channels + 6 sports channels',
                p.sales_period_start = '04/06/2012 00:00:00',
                p.sales_period_end = NULL,
                p.baseline_price = 100
WHEN NOT MATCHED THEN 
    INSERT (p.product_id,P.NAME ,P.DESCRIPTION,P.SALES_PERIOD_START,P.sales_period_end,P.BASELINE_PRICE)
    values(12103,'IPTVSport','8 bacis channels + 6 sports channels','04/06/2012 00:00:00',NULL,100);
    
MERGE INTO PRODUCT p
 USING (select 1 from dual)
 ON ( P.PRODUCT_ID = 12104)
 WHEN MATCHED THEN
    UPDATE SET  p.name = 'IPoEUnlim100',
                p.description = 'IP over Ethernet, speed to 100 Mbit/s',
                p.sales_period_start = '08/08/2010 00:00:00',
                p.sales_period_end = NULL,
                p.baseline_price = 1000
WHEN NOT MATCHED THEN 
    INSERT (p.product_id,P.NAME ,P.DESCRIPTION,P.SALES_PERIOD_START,P.sales_period_end,P.BASELINE_PRICE)
    values(12104,'IPoEUnlim100','IP over Ethernet, speed to 100 Mbit/s','08/08/2010 00:00:00',NULL,1000);
    
MERGE INTO PRODUCT p
 USING (select 1 from dual)
 ON ( P.PRODUCT_ID = 12105)
 WHEN MATCHED THEN
    UPDATE SET  p.name = 'IPoEUnlim60',
                p.description = 'IP over Ethernet, speed to 60 Mbit/s',
                p.sales_period_start = '11/04/2010 00:00:00',
                p.sales_period_end = NULL,
                p.baseline_price = 600
WHEN NOT MATCHED THEN 
    INSERT (p.product_id,P.NAME ,P.DESCRIPTION,P.SALES_PERIOD_START,P.sales_period_end,P.BASELINE_PRICE)
    values(12105,'IPoEUnlim60','IP over Ethernet, speed to 60 Mbit/s','11/04/2010 00:00:00',NULL,600);    
               
MERGE INTO PRODUCT p
 USING (select 1 from dual)
 ON ( P.PRODUCT_ID = 12106)
 WHEN MATCHED THEN
    UPDATE SET  p.name = 'IPoEBasic80',
                p.description = 'IP over Ethernet, speed to 80 Mbit/s, after 300 Gb - speed is limited',
                p.sales_period_start = '20/05/2010 00:00:00',
                p.sales_period_end = NULL,
                p.baseline_price = 400
WHEN NOT MATCHED THEN 
    INSERT (p.product_id,P.NAME ,P.DESCRIPTION,P.SALES_PERIOD_START,P.sales_period_end,P.BASELINE_PRICE)
    values(12106,'IPoEBasic80','IP over Ethernet, speed to 80 Mbit/s, after 300 Gb - speed is limited','20/05/2010 00:00:00',NULL,400);
    commit;    
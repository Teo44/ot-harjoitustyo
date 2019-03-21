
package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void kassanRahamaaraAlussaOikein()   {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void myytyjenLuonaidenMaaraAlussaOikein()    {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty() + kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void edullisenKateisostoKassanRahamaaraTasaraha()    {
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void edullisenKateisostoKassanRahamaaraEiTasaraha()  {
        kassa.syoEdullisesti(300);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void edullisenKateisostoVaihtorahaTasarahalla()  {
        assertEquals(0, kassa.syoEdullisesti(240));
    }
    
    @Test
    public void edullisenKateisostonVaihtorahaEiTasarahalla()   {
        assertEquals(60, kassa.syoEdullisesti(300));
    }
    
    @Test
    public void edullisenKateisostoMaksuEiRiittavaKassanRahamaara()    {
        kassa.syoEdullisesti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void edullisenKateisostoMaksuEiRiittavaVaihtoRaha()  {
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    
    @Test
    public void edullisenKateisostoMaksuEiRiittavaMyytyjenMaara()   {
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanKateisostoKassanRahamaaraTasaraha()    {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanKateisostoKassanRahamaaraEiTasaraha()  {
        kassa.syoMaukkaasti(550);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanKateisostoVaihtorahaTasarahalla()  {
        assertEquals(0, kassa.syoMaukkaasti(400));
    }
    
    @Test
    public void maukkaanKateisostonVaihtorahaEiTasarahalla()   {
        assertEquals(150, kassa.syoMaukkaasti(550));
    }
    
    @Test
    public void maukkaanKateisostoMaksuEiRiittavaKassanRahamaara()    {
        kassa.syoMaukkaasti(350);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanKateisostoMaksuEiRiittavaVaihtoRaha()  {
        assertEquals(350, kassa.syoMaukkaasti(350));
    }
    
    @Test
    public void maukkaanKateisostoMaksuEiRiittavaMyytyjenMaara()   {
        kassa.syoMaukkaasti(350);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    
    // testit maksukortin kanssa
    
    @Test
    public void edullisenKorttiostoKassanRahamaaraOikein()  {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void edullisenKorttiostoMyytyjenMaaraOikein()    {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void edullisenKorttiostoKortinKateOikein()   {
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void edullisenKorttiostoPalautusarvoOikein() {
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void edullisenKorttiosto2MyytyjenMaaraOikein()   {
        kortti.otaRahaa(800);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void edullisenKorttiosto2KortinKateOikein()  {
        kortti.otaRahaa(800);
        kassa.syoEdullisesti(kortti);
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void edullisenKorttiosto2PalautusarvoOikein()    {
        kortti.otaRahaa(800);
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    
    
    @Test
    public void maukkaanKorttiostoKassanRahamaaraOikein()  {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void maukkaanKorttiostoMyytyjenMaaraOikein()    {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanKorttiostoKortinKateOikein()   {
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void maukkaanKorttiostoPalautusarvoOikein() {
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void maukkaanKorttiosto2MyytyjenMaaraOikein()   {
        kortti.otaRahaa(700);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanKorttiosto2KortinKateOikein()  {
        kortti.otaRahaa(700);
        kassa.syoMaukkaasti(kortti);
        assertEquals(300, kortti.saldo());
    }
    
    @Test
    public void maukkaanKorttiosto2PalautusarvoOikein()    {
        kortti.otaRahaa(700);
        assertFalse(kassa.syoMaukkaasti(kortti));
    }
    
    // rahan lataaminen kortille
    
    @Test
    public void rahanLataaminenKortilleKortinSaldoOikein()  {
        kassa.lataaRahaaKortille(kortti, 200);
        assertEquals(1200, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKortilleKassanSaldoKasvaa()  {
        kassa.lataaRahaaKortille(kortti, 200);
        assertEquals(100200, kassa.kassassaRahaa());
    }
    
    @Test
    public void rahanLataaminenKortilleSumma0KortinSaldoOikein()    {
        kassa.lataaRahaaKortille(kortti, 0);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKortilleSumma0KassanSaldoOikein()    {
        kassa.lataaRahaaKortille(kortti, 0);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void rahanLataaminenKortilleSummaNegatiivinenKortinSaldoOikein()    {
        kassa.lataaRahaaKortille(kortti, -123);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKortilleSummaNegatiivinenKassanSaldoOikein()    {
        kassa.lataaRahaaKortille(kortti, -123);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    
}























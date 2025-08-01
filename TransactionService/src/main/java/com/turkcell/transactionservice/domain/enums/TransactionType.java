package com.turkcell.transactionservice.domain.enums;

public enum TransactionType {
    EFT,                  // Farklı bankalar arası transfer
    HAVALE,               // Aynı banka içi transfer
    FAST,                 // Anlık transfer (kolay adresle olabilir)
    VIRMAN,               // Aynı kişiye ait hesaplar arası transfer

    KREDI_ODENMESI,       // Kredi taksiti ödemesi
    FATURA_ODEME,         // Elektrik, su, internet vb. fatura ödemesi
    VERGI_ODEME,          // Vergi ödemeleri

    MAAS_ODENMESI,        // İşverenin yaptığı maaş ödemesi
    ALICI_TAHSILATI,      // Ödeme alınması (ör. e-ticaret satışı)

    POS_HARCAMASI,        // Kartla alışveriş
    NAKIT_CEKIM,          // ATM'den nakit çekme
    NAKIT_YATIRMA,        // ATM veya vezneden para yatırma

    KART_BORC_ODENMESI,   // Kredi kartı ekstresi ödemesi
    AUTOMATIC_PAYMENT,    // Otomatik talimat ile ödeme

    PARA_IADE,

    YURTDISI_TRANSFER,    // SWIFT, IBAN dışı transferler
    DIGER                 // Tanımsız/manuel işlem
}
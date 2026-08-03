/**
 * SDUI Banking Demo – Mock API Server
 * Çalıştırmak için: node server.js
 * Endpoint: GET http://localhost:3000/api/page
 *           GET http://localhost:3000/api/transfer
 */

const http = require("http");
const PORT = 3000;

const transferPage = {
  page_title: "Hızlı Para Transferi",
  components: [
    {
      type: "transfer_search",
      placeholder: "Alıcı, hesap no, açıklama ara"
    },
    {
      type: "transfer_favorites",
      title: "Favoriler",
      item_count: 5,
      favorites: [
        { id: "1", label: "Masa tenisi h...", name: "Sabahattin Sabrioğlu", initials: "SS", color: "#E84118" },
        { id: "2", label: "Annem",           name: "Fatma Yılmaz",          initials: "FY", color: "#9C27B0" },
        { id: "3", label: "Kira",            name: "Mehmet Karabacak",      initials: "MK", color: "#E84118" },
        { id: "4", label: "Abim",            name: "Mahmut Yılmaz",         initials: "MY", color: "#FF9800" }
      ]
    },
    {
      type: "transfer_history",
      title: "Son Transferler",
      item_count: 3,
      transfers: [
        { id: "1", type: "Fast",   date: "03.03.2026", name: "Sabahattin Sabrioğlu", masked_iban: "TR70 00 •••• 6972 01", amount: "₺2.000,00" },
        { id: "2", type: "EFT",    date: "03.03.2026", name: "Mehmet Karabulut",     masked_iban: "TR12 00 •••• 6150 01", amount: "₺4.750,00" },
        { id: "3", type: "Havale", date: "03.03.2026", name: "Fatma Yılmaz",         masked_iban: "TR23 00 •••• 8413 36", amount: "₺1.250,00" }
      ]
    }
  ]
};

const sduiPage = {
  page_title: "Anasayfa",
  components: [
    {
      type: "bank_header",
      logo_text: "alBaraka",
      user_name: "Ahmet"
    },
    {
      type: "account_overview",
      sdui_tabs: [
        { id: "accounts", label: "Hesaplar" },
        { id: "cards",    label: "Kartlar"  }
      ],
      accounts: [
        {
          id: "1",
          name: "Cari TL Hesabı",
          balance: "1.245,00",
          currency: "TRY",
          iban: "TR90 0020 3000 7896 5401 0000 01",
          actions: [
            { label: "Para Transferi", action: "transfer",     icon: "send"      },
            { label: "Son İşlemler",   action: "transactions", icon: "history"   },
            { label: "Diğer",          action: "more",         icon: "more_horiz"}
          ]
        }
      ],
      bank_cards: [
        { id: "1", name: "Gold Worldcard",    last_four: "1187", card_type: "VISA", color: "#B8922A" },
        { id: "2", name: "Trend Kredi Kartı", last_four: "5978", card_type: "VISA", color: "#E84118" }
      ]
    },
    {
      type: "promo_banner",
      text: "₺20.000 hesabınıza geçmeye hazır. Hemen kullanın.",
      action: "use_now"
    },
    {
      type: "net_wealth_accordion",
      title: "Net Varlığım",
      amount: "₺45.320,00"
    },
    {
      type: "quick_actions_section",
      tab_labels: ["Hızlı İşlemler", "Son Hareketler", "Bekleyen İşlemler"],
      bank_actions: [
        { label: "Hızlı Para Transferi",       action: "quick_transfer", icon: "send"         },
        { label: "TR Karekod (QR) İşlemleri",  action: "qr",             icon: "qr_code"      },
        { label: "Döviz/Kıymetli Madenler",    action: "forex",          icon: "swap_horiz"   },
        { label: "Kart Borç Ödemesi",          action: "card_payment",   icon: "credit_card"  }
      ]
    },
    {
      type: "campaign_section_header",
      title: "Kampanyalar",
      action_label: "Tümü"
    }
  ]
};

const server = http.createServer((req, res) => {
  res.setHeader("Access-Control-Allow-Origin", "*");

  const respond = (data) => {
    const body = JSON.stringify(data, null, 2);
    res.writeHead(200, { "Content-Type": "application/json", "Content-Length": Buffer.byteLength(body) });
    res.end(body);
    console.log(`[${new Date().toISOString()}] GET ${req.url} → 200`);
  };

  if (req.method === "GET" && req.url === "/api/page") {
    respond(sduiPage);
  } else if (req.method === "GET" && req.url === "/api/transfer") {
    respond(transferPage);
  } else {
    res.writeHead(404, { "Content-Type": "application/json" });
    res.end(JSON.stringify({ error: "Not found" }));
  }
});

server.listen(PORT, () => {
  console.log(`SDUI Banking server: http://localhost:${PORT}/api/page`);
  console.log(`                     http://localhost:${PORT}/api/transfer`);
  console.log(`Android emülatör:    http://10.0.2.2:${PORT}/api/page`);
});

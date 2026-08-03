module.exports = (req, res) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.status(200).json({
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
          {
            id: "1", type: "Fast", date: "03.03.2026",
            name: "Sabahattin Sabrioğlu", masked_iban: "TR70 00 •••• 6972 01", amount: "₺2.000,00"
          },
          {
            id: "2", type: "EFT", date: "03.03.2026",
            name: "Mehmet Karabulut", masked_iban: "TR12 00 •••• 6150 01", amount: "₺4.750,00"
          },
          {
            id: "3", type: "Havale", date: "03.03.2026",
            name: "Fatma Yılmaz", masked_iban: "TR23 00 •••• 8413 36", amount: "₺1.250,00"
          }
        ]
      }
    ]
  });
};

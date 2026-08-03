package com.demo.sdui.data.model

// ─── Raw JSON models (directly mapped from server response) ───────────────────

data class SduiPage(
    val page_title: String,
    val components: List<SduiRawComponent>
)

data class SduiRawComponent(
    val type: String,
    // hero_banner
    val title: String? = null,
    val subtitle: String? = null,
    val background_color: String? = null,
    val image_url: String? = null,
    // section_header
    val text: String? = null,
    // card_list
    val cards: List<SduiCard>? = null,
    // button_row
    val buttons: List<SduiButton>? = null,
    // banking
    val logo_text: String? = null,
    val user_name: String? = null,
    val sdui_tabs: List<SduiTab>? = null,
    val accounts: List<BankAccount>? = null,
    val bank_cards: List<BankCard>? = null,
    val bank_actions: List<BankAction>? = null,
    val tab_labels: List<String>? = null,
    val amount: String? = null,
    val action_label: String? = null,
    val action: String? = null
)

data class SduiCard(
    val id: String,
    val title: String,
    val description: String,
    val badge: String? = null,
    val icon: String? = null
)

data class SduiButton(
    val label: String,
    val action: String,
    val style: String = "primary" // "primary" | "secondary" | "outlined"
)

// ─── Banking data classes ─────────────────────────────────────────────────────

data class SduiTab(val id: String, val label: String)

data class BankAction(
    val label: String,
    val action: String,
    val icon: String = ""
)

data class BankAccount(
    val id: String,
    val name: String,
    val balance: String,
    val currency: String = "TRY",
    val iban: String,
    val actions: List<BankAction> = emptyList()
)

data class BankCard(
    val id: String,
    val name: String,
    val last_four: String,
    val card_type: String = "VISA",
    val color: String = "#1A73E8"
)

// ─── Typed component model (post-parsing) ─────────────────────────────────────

sealed class SduiComponent {
    data class HeroBanner(
        val title: String,
        val subtitle: String,
        val backgroundColor: String,
        val imageUrl: String?
    ) : SduiComponent()

    data class SectionHeader(val text: String) : SduiComponent()

    data class CardList(val cards: List<SduiCard>) : SduiComponent()

    data class ButtonRow(val buttons: List<SduiButton>) : SduiComponent()

    data class Unknown(val type: String) : SduiComponent()

    // ── Banking components ───────────────────────────────────────────────────
    data class BankHeader(val logoText: String, val userName: String) : SduiComponent()
    data class AccountOverview(
        val tabs: List<SduiTab>,
        val accounts: List<BankAccount>,
        val bankCards: List<BankCard>
    ) : SduiComponent()
    data class PromoBanner(val text: String, val action: String) : SduiComponent()
    data class NetWealthAccordion(val title: String, val amount: String) : SduiComponent()
    data class QuickActionsSection(val tabLabels: List<String>, val bankActions: List<BankAction>) : SduiComponent()
    data class CampaignSectionHeader(val title: String, val actionLabel: String) : SduiComponent()
}

fun SduiRawComponent.toSduiComponent(): SduiComponent = when (type) {
    "hero_banner" -> SduiComponent.HeroBanner(
        title = title ?: "",
        subtitle = subtitle ?: "",
        backgroundColor = background_color ?: "#1A73E8",
        imageUrl = image_url
    )
    "section_header" -> SduiComponent.SectionHeader(text = text ?: "")
    "card_list" -> SduiComponent.CardList(cards = cards ?: emptyList())
    "button_row" -> SduiComponent.ButtonRow(buttons = buttons ?: emptyList())
    "bank_header" -> SduiComponent.BankHeader(
        logoText = logo_text ?: "Bank",
        userName = user_name ?: ""
    )
    "account_overview" -> SduiComponent.AccountOverview(
        tabs = sdui_tabs ?: emptyList(),
        accounts = accounts ?: emptyList(),
        bankCards = bank_cards ?: emptyList()
    )
    "promo_banner" -> SduiComponent.PromoBanner(
        text = text ?: "",
        action = action ?: ""
    )
    "net_wealth_accordion" -> SduiComponent.NetWealthAccordion(
        title = title ?: "Net Varlığım",
        amount = amount ?: ""
    )
    "quick_actions_section" -> SduiComponent.QuickActionsSection(
        tabLabels = tab_labels ?: emptyList(),
        bankActions = bank_actions ?: emptyList()
    )
    "campaign_section_header" -> SduiComponent.CampaignSectionHeader(
        title = title ?: "",
        actionLabel = action_label ?: "Tümü"
    )
    else -> SduiComponent.Unknown(type = type)
}

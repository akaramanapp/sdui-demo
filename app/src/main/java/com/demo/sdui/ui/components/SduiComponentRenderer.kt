package com.demo.sdui.ui.components

import androidx.compose.runtime.Composable
import com.demo.sdui.data.model.SduiComponent
import com.demo.sdui.ui.components.banking.AccountOverviewComponent
import com.demo.sdui.ui.components.banking.BankHeaderComponent
import com.demo.sdui.ui.components.banking.CampaignSectionComponent
import com.demo.sdui.ui.components.banking.NetWealthAccordionComponent
import com.demo.sdui.ui.components.banking.PromoBannerComponent
import com.demo.sdui.ui.components.banking.QuickActionsSectionComponent
import com.demo.sdui.ui.components.banking.TransferFavoritesComponent
import com.demo.sdui.ui.components.banking.TransferHistoryComponent
import com.demo.sdui.ui.components.banking.TransferSearchComponent

@Composable
fun SduiComponentRenderer(component: SduiComponent) {
    when (component) {
        // ── Legacy components ────────────────────────────────────────────────
        is SduiComponent.HeroBanner    -> HeroBannerComponent(component)
        is SduiComponent.SectionHeader -> SectionHeaderComponent(component)
        is SduiComponent.CardList      -> CardListComponent(component)
        is SduiComponent.ButtonRow     -> ButtonRowComponent(component)
        // ── Banking components ───────────────────────────────────────────────
        is SduiComponent.BankHeader          -> BankHeaderComponent(component)
        is SduiComponent.AccountOverview     -> AccountOverviewComponent(component)
        is SduiComponent.PromoBanner         -> PromoBannerComponent(component)
        is SduiComponent.NetWealthAccordion  -> NetWealthAccordionComponent(component)
        is SduiComponent.QuickActionsSection -> QuickActionsSectionComponent(component)
        is SduiComponent.CampaignSectionHeader -> CampaignSectionComponent(component)        // ── Transfer screen components ─────────────────────────────────────────────
        is SduiComponent.TransferSearch    -> TransferSearchComponent(component)
        is SduiComponent.TransferFavorites -> TransferFavoritesComponent(component)
        is SduiComponent.TransferHistory   -> TransferHistoryComponent(component)        // ── Fallback ─────────────────────────────────────────────────────────
        is SduiComponent.Unknown -> { /* silently skip unknown types */ }
    }
}

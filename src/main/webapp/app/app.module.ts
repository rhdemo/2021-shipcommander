import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ShipcommanderSharedModule } from 'app/shared/shared.module';
import { ShipcommanderCoreModule } from 'app/core/core.module';
import { ShipcommanderAppRoutingModule } from './app-routing.module';
import { ShipcommanderHomeModule } from './home/home.module';
import { ShipcommanderEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ShipcommanderSharedModule,
    ShipcommanderCoreModule,
    ShipcommanderHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ShipcommanderEntityModule,
    ShipcommanderAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class ShipcommanderAppModule {}

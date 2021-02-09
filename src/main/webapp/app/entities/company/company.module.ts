import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShipcommanderSharedModule } from 'app/shared/shared.module';
import { CompanyComponent } from './company.component';
import { CompanyDetailComponent } from './company-detail.component';
import { CompanyUpdateComponent } from './company-update.component';
import { CompanyDeleteDialogComponent } from './company-delete-dialog.component';
import { companyRoute } from './company.route';

@NgModule({
  imports: [ShipcommanderSharedModule, RouterModule.forChild(companyRoute)],
  declarations: [CompanyComponent, CompanyDetailComponent, CompanyUpdateComponent, CompanyDeleteDialogComponent],
  entryComponents: [CompanyDeleteDialogComponent],
})
export class ShipcommanderCompanyModule {}

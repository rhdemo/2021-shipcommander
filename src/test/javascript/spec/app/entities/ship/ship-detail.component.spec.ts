import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShipcommanderTestModule } from '../../../test.module';
import { ShipDetailComponent } from 'app/entities/ship/ship-detail.component';
import { Ship } from 'app/shared/model/ship.model';

describe('Component Tests', () => {
  describe('Ship Management Detail Component', () => {
    let comp: ShipDetailComponent;
    let fixture: ComponentFixture<ShipDetailComponent>;
    const route = ({ data: of({ ship: new Ship(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShipcommanderTestModule],
        declarations: [ShipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ShipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ship on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ship).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

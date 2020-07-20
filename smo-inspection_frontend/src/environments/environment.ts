// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: true,
  oktaBaseURL: 'https://swirecnco.okta.com',
  oktaConfig: {
    issuer: 'https://swirecnco.okta.com',
    redirectUri: 'http://smafedev.swiredigital-s3-staging.com/assets/callback.html',
    clientId: '0oa4e5y1tmE5JmsBd357',
    scope: 'openid email profile'
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.

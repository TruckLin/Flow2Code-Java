 i m p o r t   j a v a . u t i l . S c a n n e r ;   
 p u b l i c   c l a s s   F l o w C o d e { 
         p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g [ ]   a r g s ) { 
                 S c a n n e r   s c   =   n e w   S c a n n e r ( S y s t e m . i n ) ; 
 
                 i n t   s e c r e t ; 
                 i n t   m y G u e s s ; 
 
                 s e c r e t   =   1 0 2 5 ; 
                 S y s t e m . o u t . p r i n t l n (   " W e l c o m e ,   e n t e r   y o u r   f i r s t   g u e s s   :   "   ) ; 
                 m y G u e s s   =   s c . n e x t I n t ( ) ; 
                 w h i l e (   s e c r e t   ! =   m y G u e s s   )   { 
                         i f (   s e c r e t   >   m y G u e s s   )   { 
                                 S y s t e m . o u t . p r i n t l n (   " H i g h e r ,   t r y   a g a i n   :   "   ) ; 
                         }   e l s e   { 
                                 S y s t e m . o u t . p r i n t l n (   " L o w e r ,   t r y   a g a i n   :   "   ) ; 
                         } 
                         m y G u e s s   =   s c . n e x t I n t ( ) ; 
                 } 
                 S y s t e m . o u t . p r i n t l n (   " T h a t ' s   c o r r e c t . "   ) ; 
                 s c . c l o s e ( ) ; 
         } 
 }